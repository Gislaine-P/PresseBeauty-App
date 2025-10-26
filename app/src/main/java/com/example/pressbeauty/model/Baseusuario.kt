package com.example.pressbeauty.model
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsuarioUI::class], version = 1)
abstract class Baseusuario: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    companion object {
        @Volatile
        private var INSTANCE: Baseusuario? = null

        fun getDatabase(context: Context): Baseusuario {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Baseusuario::class.java,
                    "pressbeauty_db"
                    ).build()
                    INSTANCE = instance
                    instance
            }
        }
    }
}