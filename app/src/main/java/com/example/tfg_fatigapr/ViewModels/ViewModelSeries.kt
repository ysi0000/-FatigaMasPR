package com.example.tfg_fatigapr.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.tfg_fatigapr.Fragmentos.DatosPR
import com.example.tfg_fatigapr.Utilidades.RoomDataBase
import com.example.tfg_fatigapr.clasesDatos.Serie

class ViewModelSeries(context: Context):ViewModel(){
    private val db=
        RoomDataBase.getInstance(context)!!
    private val seriesDao=db.serieDAO()
    var series=seriesDao.seleccionarSeries()
    private var dia:String=""
    /**
     * Permite insertar una serie a un ejercicio
     */
    fun insertarSerie(idEjercicio: Int){
        seriesDao.insertarSerie(Serie(numeroSeries(dia,idEjercicio),0,0,0,dia,idEjercicio))
    }

    /**
     * Permite borrar una serie de un ejercicio
     */
    fun borrarSerie(serie: Serie){
        seriesDao.eliminarSerie(serie)
    }

    /**
     * Permite seleccionar todas las series
     */
    fun seleccionarSeries() {
        series=seriesDao.seleccionarSeries()
    }

    /**
     * Permite seleccionar las series de un dia especifico
     */
    fun seleccionarSeriesDia(dia:String){
        series=seriesDao.seleccionarSeriesDia(dia)
    }

    /**
     * Permite seleccionar series a partir de una serie y un ejercicio
     */
    fun seleccionarSeriesdeEjercicio(dia:String,idEjercicio: Int){
        series=seriesDao.seleccionarSeriesdeEjercicio(dia,idEjercicio)
    }

    /**
     * Permite conocer el numero de series de un ejercicio en un dia
     */
    fun numeroSeries(dia:String,idEjercicio: Int):Int{
        return seriesDao.numeroSeries(dia,idEjercicio)
    }

    /**
     * Actualiza el RPE de una serie en un ejercicio
     */
    fun actualizarRPE(RPE:Int,idEjercicio: Int,Id:Int)
    {
        seriesDao.actualizarRPE(RPE,dia,idEjercicio,Id)
    }
    /**
     * Actualiza el peso de una serie en un ejercicio
     */
    fun actualizarPeso(peso:Int,idEjercicio: Int,Id:Int)
    {
        seriesDao.actualizarPeso(peso,dia,idEjercicio,Id)
    }
    /**
     * Actualiza las repeticiones de una serie en un ejercicio
     */
    fun actualizarReps(Reps:Int,idEjercicio: Int,Id:Int)
    {
        seriesDao.actualizarReps(Reps,dia,idEjercicio,Id)
    }
    /**
     * Actualiza el dia actual
     */
    fun ponderDia(diaActual:String){
        dia=diaActual
    }

    /**
     * Selecciona la serie mas dura del dia
     */
    fun seriesPorEjYDia(ejercicio:String, modificacion:String, dia:String): DatosPR? {
        val series=seriesDao.seriesPorEjYDia(ejercicio, modificacion, dia)
        return if(series.isNotEmpty())
            series[0]
        else null
    }

}