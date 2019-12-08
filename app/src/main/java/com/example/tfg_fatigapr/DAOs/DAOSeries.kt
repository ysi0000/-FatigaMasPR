package com.example.tfg_fatigapr.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tfg_fatigapr.clasesDatos.Serie

@Dao
interface DAOSeries{
    @Query("SELECT * FROM tbSeries")
    fun seleccionarSeries():List<Serie>

    @Insert
    fun insertarSerie(serie: Serie)

    @Delete
    fun eliminarSerie(serie:Serie)
}