package com.example.tfg_fatigapr.ViewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.Utilidades.RoomDataBase
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class ViewModelEjercicios(private val aplication: Application): AndroidViewModel(aplication) {
    private val context=aplication.applicationContext!!
    private val db=
        RoomDataBase.getInstance(context)!!
    private var sharedPreferences: SharedPreferences= PreferenceManager.getDefaultSharedPreferences(context)
    private var mFireBaseAuth: FirebaseAuth=FirebaseAuth.getInstance()
    private var usuario=sharedPreferences.getString(context.getString(R.string.key_editpref_nombre),
        mFireBaseAuth.currentUser!!.displayName)!!
    private val ejerciciosDao=db.ejercicioDAO()
    private var _dia=MutableLiveData(SimpleDateFormat("d-M-yyyy", Locale.FRANCE).format(Date()))
    private val _ejercicios=MutableLiveData(ejerciciosDao.seleccionarEjercicios(_dia.value!!,usuario))
    val dia:LiveData<String>
        get()=_dia
    val ejercicios:LiveData<List<Ejercicio>>
        get()=_ejercicios

    /**
     * Funcion para insertar ejercicio en la base de datos
     */
    fun insertarEjercicio(ejercicio: Ejercicio){
        ejerciciosDao.añadirEjercicio(ejercicio)
    }
    /**
     * Funcion para borrar ejercicio en la base de datos
     */
    fun borrarEjercicio(ejercicio: Ejercicio){
        ejerciciosDao.eliminarEjercicio(ejercicio)

    }
    /**
     * Funcion para insertar ejercicio en la base de datos
     */
    fun seleccionarEjercicios() {
        _ejercicios.value= ejerciciosDao.seleccionarEjercicios(usuario)
    }

    /**
     * Funcion para seleccionar un ejercicio de la base de datos
     */
    fun seleccionarEjerciciosDia() {
        _ejercicios.value=ejerciciosDao.seleccionarEjercicios(_dia.value!!,usuario)
    }

    /**
     * funcion que devulve el numero de ejercicios de un dia
     */
    fun ejerciciosDia(): Int {
        return ejerciciosDao.ejerciciosDia(_dia.value!!,usuario)
    }
    /**
     * funcion que devulve el numero de ejercicios de un dia a partir de uno especifico
     */
    fun ejerciciosDia(dia:String): Int {
        return ejerciciosDao.ejerciciosDia(dia,usuario)
    }
    /**
     *
     */
    fun modificarDia(operacion:Int): Calendar {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
        val d = dateFormat.parse(_dia.value!!)
        //}
        val c = Calendar.getInstance()
        c.time = d!!
        c.add(Calendar.DATE, operacion)
        _dia.value=context.getString(
            R.string.formatodiamesao,
            c.get(Calendar.DATE).toString(),
            (c.get(Calendar.MONTH) + 1).toString(),
            c.get(Calendar.YEAR).toString())
        return c
    }

    /**
     * Actualiza el dia a uno nuevo
     */
    fun actualizarDia(diaNuevo:String){
        _dia.value=diaNuevo
    }

}