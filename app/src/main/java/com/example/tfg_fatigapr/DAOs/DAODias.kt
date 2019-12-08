package com.example.tfg_fatigapr.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tfg_fatigapr.clasesDatos.Dia

@Dao
interface DAODias {
    @Query("SELECT * FROM tbDias")
    fun seleccionarDias():List<Dia>

    @Insert
    fun insertarDia(dia:Dia)

    @Delete
    fun eliminarDia(dia:Dia)
}