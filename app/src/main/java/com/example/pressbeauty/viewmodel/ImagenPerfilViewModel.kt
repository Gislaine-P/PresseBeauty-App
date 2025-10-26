package com.example.pressbeauty.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pressbeauty.datastore.ImagenPerfilDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class ImagenPerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = ImagenPerfilDataStore(application)

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imageUri : StateFlow<Uri?> = _imagenUri

    init {
        viewModelScope.launch {
            val uriString = dataStore.obtenerImagen().first()
            if(!uriString.isNullOrEmpty()){
                _imagenUri.value = Uri.parse(uriString)
            }
        }
    }

    fun guardarImagenPermanente(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = File(context.filesDir, "perfil.jpg")
                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                val newUri = Uri.fromFile(file)
                dataStore.guardarImagen(newUri.toString())
                _imagenUri.value = newUri
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun setImage(uri: Uri?){
        viewModelScope.launch {
            if(uri != null){
                dataStore.guardarImagen(uri.toString())
            }else{
                dataStore.limpiarImagen()
            }
            _imagenUri.value = uri
        }
    }
}