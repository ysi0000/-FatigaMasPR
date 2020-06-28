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

    fun seleccionarEjercicios(nombreUsuario:String): List<Ejercicio> {
        return ejerciciosDao.seleccionarEjercicios(nombreUsuario)
    }

    fun seleccionarEjercicios(dia:String,nombreUsuario:String): List<Ejercicio> {
        return ejerciciosDao.seleccionarEjercicios(dia,nombreUsuario)
    }

    fun ejerciciosDia(dia:String,nombreUsuario:String): Int {
        return ejerciciosDao.ejerciciosDia(dia,nombreUsuario)
    }
}