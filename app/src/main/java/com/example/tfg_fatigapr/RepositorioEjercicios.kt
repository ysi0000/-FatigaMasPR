package com.example.tfg_fatigapr

import android.app.Application
import com.example.tfg_fatigapr.DAOs.DAOEjercicios
import com.example.tfg_fatigapr.Utilidades.RoomDataBase
import com.example.tfg_fatigapr.clasesDatos.Ejercicio

class RepositorioEjercicios(application: Application){
    private var ejerciciosDao:DAOEjercicios

    init {
        val db= RoomDataBase.getInstance(application)!!
        ejerciciosDao=db.ejercicioDAO()
    }

    fun insertarEjercicio(ejercicio: Ejercicio){
        ejerciciosDao.añadirEjercicio(ejercicio)
    }

    fun borrarEjercicio(ejercicio: Ejercicio){
        ejerciciosDao.eliminarEjercicio(ejercicio)
    }

    fun seleccionarEjercicios(): List<Ejercicio> {
        return ejerciciosDao.seleccionarEjercicios()
    }

    fun seleccionarEjercicios(dia:String): List<Ejercicio> {
        return ejerciciosDao.seleccionarEjercicios(dia)
    }

    fun ejerciciosDia(dia:String): Int {
        return ejerciciosDao.ejerciciosDia(dia)
    }
}