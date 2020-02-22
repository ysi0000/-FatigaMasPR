package com.example.tfg_fatigapr.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.Utilidades.RoomDataBase
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import java.text.SimpleDateFormat
import java.util.*

class ViewModelEjercicios(context:Context): ViewModel() {

    private val context=context
    private val db=
        RoomDataBase.getInstance(context)!!
    private val ejerciciosDao=db.ejercicioDAO()
    var dia:String=SimpleDateFormat("d-M-yyyy", Locale.FRANCE).format(Date())

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

    fun ejerciciosDia(dia: String): Int {
        return ejerciciosDao.ejerciciosDia(dia)
    }

    fun modificarDia(operacion:Int): Calendar {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
        val d = dateFormat.parse(dia)
        //}
        val c = Calendar.getInstance()
        c.time = d!!
        c.add(Calendar.DATE, operacion)
        dia=context.getString(
            R.string.formatodiamesao,
            c.get(Calendar.DATE).toString(),
            (c.get(Calendar.MONTH) + 1).toString(),
            c.get(Calendar.YEAR).toString())
        return c
    }

    fun actualizarDiaDP(diaDP:String){
        dia=diaDP
    }

}