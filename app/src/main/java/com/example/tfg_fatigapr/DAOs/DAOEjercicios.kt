package com.example.tfg_fatigapr.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tfg_fatigapr.clasesDatos.Ejercicio

@Dao
interface DAOEjercicios {
    @Query("SELECT * FROM tbEjercicios")
    fun seleccionarEjercicios():List<Ejercicio>

    @Insert
    fun añadirEjercicio(ejercicio: Ejercicio)

    @Delete
    fun eliminarEjercicio(ejercicio: Ejercicio)

}