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
import org.mockito.kotlin.*
import java.io.ByteArrayInputStream
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class ImagenPerfilViewModelTest {

    private lateinit var viewModel: ImagenPerfilViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val mockApp = mock<Application>()
    private val mockContext = mock<Context>()
    private val mockDataStore = mock<ImagenPerfilDataStore>()
    private val mockResolver = mock<ContentResolver>()

    private val fakeFlow = MutableStateFlow<String?>(null)

    @Before
    fun setup() = runTest {
        // NECESARIO PARA CORRER VIEWMODELS EN TESTS
        Dispatchers.setMain(testDispatcher)

        whenever(mockDataStore.obtenerImagen()).thenReturn(fakeFlow)
        whenever(mockContext.contentResolver).thenReturn(mockResolver)

        viewModel = object : ImagenPerfilViewModel(mockApp) {
            override val dataStore = mockDataStore
        }

        // Ejecuta coroutines pendientes del init {}
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
        fakeFlow.value = "file://perfil.jpg"

        val vm = object : ImagenPerfilViewModel(mockApp) {
            override val dataStore = mockDataStore
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(Uri.parse("file://perfil.jpg"), vm.imageUri.value)
    }

    @Test
    fun `setImage actualiza el StateFlow y guarda en DataStore`() = runTest {
        val uri = Uri.parse("file://test.jpg")

        viewModel.setImage(uri)

        assertEquals(uri, viewModel.imageUri.value)
        verify(mockDataStore).guardarImagen(uri.toString())
    }

    @Test
    fun `setImage con null limpia imagen`() = runTest {
        viewModel.setImage(null)

        assertNull(viewModel.imageUri.value)
        verify(mockDataStore).limpiarImagen()
    }

    @Test
    fun `guardarImagenPermanente copia archivo y actualiza imagen`() = runTest {
        val uri = Uri.parse("file://foto_original.jpg")

        val fakeInput = ByteArrayInputStream("datos_falsos".toByteArray())
        whenever(mockResolver.openInputStream(uri)).thenReturn(fakeInput)

        whenever(mockContext.filesDir).thenReturn(File("./"))

        viewModel.guardarImagenPermanente(mockContext, uri)

        testDispatcher.scheduler.advanceUntilIdle()

        verify(mockDataStore).guardarImagen(any())

        assertNotNull(viewModel.imageUri.value)
        assertTrue(viewModel.imageUri.value.toString().contains("perfil.jpg"))
    }

}
