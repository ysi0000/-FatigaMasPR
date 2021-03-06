package com.example.tfg_fatigapr.Utilidades

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.tfg_fatigapr.DAOs.DAOEjercicios
import com.example.tfg_fatigapr.DAOs.DAOSeries
import com.example.tfg_fatigapr.clasesDatos.*

/**
 * Clase que crea un singleton sincronizado para que la llamada a la base de datos sea unica,
 * es decir que no haya bases de datos con estados inconcluentes.
 * Solo existe una base de datos y se devuelve la misma instancia siempre
 */
@Database(entities = [Ejercicio::class, Serie::class],version=1)
abstract class RoomDataBase: RoomDatabase() {

    abstract fun ejercicioDAO():DAOEjercicios
    abstract fun serieDAO():DAOSeries

    companion object {
        private const val DATABASE_NAME = "bd_fpr"
        @Volatile
        private var INSTANCE: RoomDataBase? = null

        fun getInstance(context: Context): RoomDataBase? {
            INSTANCE
                ?: synchronized(this) {
                INSTANCE = databaseBuilder(
                    context.applicationContext,
                    RoomDataBase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}