package com.example.tfg_fatigapr

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tfg_fatigapr.DAOs.DAOEjercicios
import com.example.tfg_fatigapr.DAOs.DAOSeries
import com.example.tfg_fatigapr.clasesDatos.*

@Database(entities = [Ejercicio::class, Serie::class],version=1)
abstract class RoomDataBase: RoomDatabase() {

    abstract fun ejercicioDAO():DAOEjercicios
    abstract fun serieDAO():DAOSeries

    companion object {
        private const val DATABASE_NAME = "bd_fpr"
        @Volatile
        private var INSTANCE: RoomDataBase? = null

        fun getInstance(context: Context): RoomDataBase? {
            INSTANCE ?: synchronized(this) {
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