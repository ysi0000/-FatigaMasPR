package com.example.tfg_fatigapr.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tfg_fatigapr.clasesDatos.Serie
import com.example.tfg_fatigapr.Fragmentos.DatosPR

@Dao
interface DAOSeries{
    @Query("SELECT * FROM tbSeries")
    fun seleccionarSeries():MutableList<Serie>

    @Insert
    fun insertarSerie(serie: Serie)

    @Delete
    fun eliminarSerie(serie:Serie)

    @Query("SELECT * FROM tbSeries WHERE dia=:dia and idEjercicio=:idEjercicio")
    fun seleccionarSeriesdeEjercicio(dia:String,idEjercicio: Int):MutableList<Serie>

    @Query("SELECT * FROM tbSeries WHERE dia=:dia")
    fun seleccionarSeriesDia(dia:String):MutableList<Serie>

    @Query("SELECT COUNT(*) FROM tbSeries WHERE dia=:dia AND idEjercicio=:idEjercicio")
    fun numeroSeries(dia:String,idEjercicio: Int):Int

    @Query("UPDATE tbSeries SET RPE=:RPE WHERE Dia=:dia AND IdEjercicio=:idEjercicio AND Id=:Id")
    fun actualizarRPE(RPE:Int,dia: String,idEjercicio: Int,Id:Int)

    @Query("UPDATE tbSeries SET Peso=:peso WHERE Dia=:dia AND IdEjercicio=:idEjercicio AND Id=:Id")
    fun actualizarPeso(peso:Int,dia: String,idEjercicio: Int,Id:Int)

    @Query("UPDATE tbSeries SET Reps=:reps WHERE Dia=:dia AND IdEjercicio=:idEjercicio AND Id=:Id")
    fun actualizarReps(reps:Int,dia: String,idEjercicio: Int,Id:Int)

    @Query("SELECT tbSeries.Reps,tbSeries.Peso,tbSeries.RPE FROM tbSeries JOIN tbEjercicios ON tbSeries.Dia=tbEjercicios.Dia WHERE tbSeries.Dia=:dia AND tbEjercicios.Nombre=:ejercicio AND tbEjercicios.Modificaciones=:modificacion ORDER BY Reps ASC,Peso DESC,RPE DESC" )
    fun seriesPorEjYDia(ejercicio:String,modificacion:String,dia:String):List<DatosPR>

}