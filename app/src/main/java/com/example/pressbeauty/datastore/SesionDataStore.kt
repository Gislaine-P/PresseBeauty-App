package com.example.pressbeauty.basestore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "usuario-preferencias")

class SesionDataStore(private val context: Context) {

    private val SESION_ACTIVA = booleanPreferencesKey("sesion_activa")
    private val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    private val CORREO_USUARIO = stringPreferencesKey("correo_usuario")
    private val DIRECCION_USUARIO = stringPreferencesKey("direccion_usuario")

    suspend fun guardarSesionActiva(valor: Boolean) {
        context.dataStore.edit { preferencias ->
            preferencias[SESION_ACTIVA] = valor
        }
    }
    suspend fun guardarDatosUsuario(nombre: String, correo: String, direccion: String) {
        context.dataStore.edit { preferencias ->
            preferencias[NOMBRE_USUARIO] = nombre
            preferencias[CORREO_USUARIO] = correo
            preferencias[DIRECCION_USUARIO] = direccion
        }
    }
    fun obtenerSesionActiva(): Flow<Boolean?> {
        return context.dataStore.data.map { preferencias ->
            preferencias[SESION_ACTIVA]
        }
    }
    fun obtenerDatosUsuario(): Flow<Map<String, String>> {
        return context.dataStore.data.map { preferencias ->
            mapOf(
                "nombre" to (preferencias[NOMBRE_USUARIO] ?: ""),
                "correo" to (preferencias[CORREO_USUARIO] ?: ""),
                "direccion" to (preferencias[DIRECCION_USUARIO] ?: "")
            )
        }
    }
    suspend fun limpiarSesion() {
        context.dataStore.edit { it.clear() }
    }
}
