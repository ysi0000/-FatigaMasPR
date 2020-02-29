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
    fun insertarSerie(idEjercicio: Int){
        seriesDao.insertarSerie(Serie(numeroSeries(dia,idEjercicio),0,0,0,dia,idEjercicio))
    }

    fun borrarSerie(serie: Serie){
        seriesDao.eliminarSerie(serie)
    }

    fun seleccionarSeries() {
        series=seriesDao.seleccionarSeries()
    }
    fun seleccionarSeriesDia(dia:String){
        series=seriesDao.seleccionarSeriesDia(dia)
    }
    fun seleccionarSeriesdeEjercicio(dia:String,idEjercicio: Int){
        series=seriesDao.seleccionarSeriesdeEjercicio(dia,idEjercicio)
    }
    fun numeroSeries(dia:String,idEjercicio: Int):Int{
        return seriesDao.numeroSeries(dia,idEjercicio)
    }
    fun actualizarRPE(RPE:Int,idEjercicio: Int,Id:Int)
    {
        seriesDao.actualizarRPE(RPE,dia,idEjercicio,Id)
    }
    fun actualizarPeso(peso:Int,idEjercicio: Int,Id:Int)
    {
        seriesDao.actualizarPeso(peso,dia,idEjercicio,Id)
    }
    fun actualizarReps(Reps:Int,idEjercicio: Int,Id:Int)
    {
        seriesDao.actualizarReps(Reps,dia,idEjercicio,Id)
    }

    fun ponderDia(diaActual:String){
        dia=diaActual
    }

    fun seriesPorEjYDia(ejercicio:String, modificacion:String, dia:String): DatosPR? {
        val series=seriesDao.seriesPorEjYDia(ejercicio, modificacion, dia)
        return if(series.isNotEmpty())
            series[0]
        else null
    }

}