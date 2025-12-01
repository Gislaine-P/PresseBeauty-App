package com.example.pressbeauty.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.pressbeauty.datastore.ImagenPerfilDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.robolectric.RobolectricTestRunner
import java.io.ByteArrayInputStream
import java.io.File

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ImagenPerfilViewModelTest {

    private lateinit var viewModel: ImagenPerfilViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val mockApp = mock<Application>()
    private val mockContext = mock<Context>()
    private val mockDataStore = mock<ImagenPerfilDataStore>()
    private val mockResolver = mock<ContentResolver>()
    private val fakeFlow = MutableStateFlow<String?>(null)

    private val MOCKED_URI_STRING_FILE = "file:///perfil.jpg"
    private val MOCKED_URI_STRING_TEST = "file:///test.jpg"
    private val MOCKED_URI_STRING_ORIGINAL = "content://media/external/images/media/1"


    @Before
    fun setup() = runTest {
        Dispatchers.setMain(testDispatcher)

        whenever(mockApp.applicationContext).thenReturn(mockContext)
        whenever(mockContext.contentResolver).thenReturn(mockResolver)
        whenever(mockContext.filesDir).thenReturn(File("./"))

        whenever(mockDataStore.obtenerImagen()).thenReturn(fakeFlow)

        viewModel = object : ImagenPerfilViewModel(mockApp) {
            override val dataStore = mockDataStore
        }

        testDispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `imagenUri inicia como null`() = runTest {
        assertNull(viewModel.imageUri.value)
    }

    @Test
    fun `init carga imagen desde DataStore si existe`() = runTest {
        fakeFlow.value = MOCKED_URI_STRING_FILE

        val vm = object : ImagenPerfilViewModel(mockApp) {
            override val dataStore = mockDataStore
        }

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(Uri.parse(MOCKED_URI_STRING_FILE), vm.imageUri.value)
    }

    @Test
    fun `setImage actualiza el StateFlow y guarda en DataStore`() = runTest {
        val uri = Uri.parse(MOCKED_URI_STRING_TEST)

        viewModel.setImage(uri)

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(uri, viewModel.imageUri.value)
        verify(mockDataStore).guardarImagen(uri.toString())
    }

    @Test
    fun `setImage con null limpia imagen`() = runTest {
        viewModel.setImage(null)

        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.imageUri.value)
        verify(mockDataStore).limpiarImagen()
    }

    @Test
    fun `guardarImagenPermanente copia archivo y actualiza imagen`() = runTest {
        val originalUri = Uri.parse(MOCKED_URI_STRING_ORIGINAL)

        val fakeInput = ByteArrayInputStream("datos_falsos".toByteArray())
        whenever(mockResolver.openInputStream(originalUri)).thenReturn(fakeInput)

        viewModel.guardarImagenPermanente(mockContext, originalUri)

        testDispatcher.scheduler.advanceUntilIdle()

        verify(mockDataStore).guardarImagen(any())

        assertNotNull(viewModel.imageUri.value)
        assertTrue(viewModel.imageUri.value.toString().contains("perfil.jpg"))
        verify(mockResolver).openInputStream(originalUri)
    }

}