package com.example.pressbeauty.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.imagenDataStore by preferencesDataStore(name = "imagen_prefs")

class ImagenPerfilDataStore(private val context: Context) {

    private val IMAGEN_KEY_URI = stringPreferencesKey("imagen_guardada")

    suspend fun guardarImagen(uri: String) {
        context.imagenDataStore.edit { prefs ->
            prefs[IMAGEN_KEY_URI] = uri
        }
    }

    fun obtenerImagen(): Flow<String?> {
        return context.imagenDataStore.data.map { prefs ->
            prefs[IMAGEN_KEY_URI]
        }
    }

    suspend fun limpiarImagen() {
        context.imagenDataStore.edit { prefs ->
            prefs.remove(IMAGEN_KEY_URI)
        }
    }
}