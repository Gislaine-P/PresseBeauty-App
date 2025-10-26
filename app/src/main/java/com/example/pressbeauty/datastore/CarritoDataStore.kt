package com.example.pressbeauty.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pressbeauty.model.CarritoUI
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "carrito_prefs")

class CarritoDataStore(private val context: Context) {

    private val gson = Gson()
    private val CARRITO_KEY = stringPreferencesKey("carrito_guardado")

    suspend fun guardarCarrito(carrito: CarritoUI) {
        val json = gson.toJson(carrito)
        context.dataStore.edit { prefs ->
            prefs[CARRITO_KEY] = json
        }
    }

    fun obtenerCarrito(): Flow<CarritoUI> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[CARRITO_KEY]
            if (json != null) gson.fromJson(json, CarritoUI::class.java)
            else CarritoUI(idCarrito = "", idUsuario = "", productos = emptyList(), total = 0)
        }
    }

    suspend fun limpiarCarrito() {
        context.dataStore.edit { prefs ->
            prefs.remove(CARRITO_KEY)
        }
    }
}