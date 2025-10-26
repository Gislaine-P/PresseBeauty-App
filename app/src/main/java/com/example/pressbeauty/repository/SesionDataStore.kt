package com.example.pressbeauty.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "sesion_usuario")

class SesionDataStore(private val context: Context) {
    companion object {
        private val ESTA_LOGUEADO = booleanPreferencesKey("esta_logueado")
    }

    val sesionIniciada: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[ESTA_LOGUEADO] ?: false
    }

    suspend fun guardarSesionActiva(activa: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ESTA_LOGUEADO] = activa
        }
    }
}

