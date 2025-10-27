package com.example.pressbeauty.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pressbeauty.repository.CompraDao
import com.example.pressbeauty.model.UsuarioDao
import com.example.pressbeauty.model.Compra


@Database(
    entities = [Usuariobase::class, Compra::class],
    version = 2
)
abstract class BaseCompra : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    abstract fun compraDao(): CompraDao

    companion object {
        @Volatile
        private var INSTANCE: BaseCompra? = null

        fun getDatabase(context: Context): BaseCompra {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseCompra::class.java,
                    "compras_basedato.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
