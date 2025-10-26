package com.example.pressbeauty.model

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room


@Database(entities = [Usuariobase::class], version = 1)
abstract class Baseusuario: RoomDatabase(){
    abstract fun usuarioDao() : UsuarioDao

    companion object{
        @Volatile
        private var INSTANCE : Baseusuario?= null

        fun getDatabase(context: Context): Baseusuario =
             INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Baseusuario::class.java,
                    "mi_baseusuario.db"
                ).build()
                INSTANCE = instance
                instance
            }
    }
}