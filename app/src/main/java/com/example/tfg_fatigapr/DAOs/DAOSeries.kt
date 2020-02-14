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


}