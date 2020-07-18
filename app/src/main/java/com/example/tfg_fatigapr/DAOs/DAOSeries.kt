package com.example.tfg_fatigapr.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tfg_fatigapr.clasesDatos.Serie
import com.example.tfg_fatigapr.Fragmentos.DatosPR
/**
 * Interfaz para realizar operaciones con la base de datos
 *
 * @author Yeray Sardón Ibañez
*/
@Dao
interface DAOSeries{
    /**
     * Funcion para seleccionar todas las series de la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("SELECT * FROM tbSeries")
    fun seleccionarSeries():MutableList<Serie>
    /**
     * Funcion para insertar una serie en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Insert
    fun insertarSerie(serie: Serie)
    /**
     * Funcion para eliminar una serie en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Delete
    fun eliminarSerie(serie:Serie)
    /**
     * Funcion para eliminar una serie en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("DELETE FROM tbSeries WHERE IdEjercicio=:idEjerecicio AND Dia=:dia")
    fun eliminarSeriesEjercicio(idEjerecicio:Int,dia:String)
    /**
     * Funcion para seleccionar series en un dia y de un ejercicio en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("SELECT * FROM tbSeries WHERE dia=:dia and idEjercicio=:idEjercicio")
    fun seleccionarSeriesdeEjercicio(dia:String,idEjercicio: Int):MutableList<Serie>
    /**
     * Funcion para seleccionar series en un dia en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("SELECT * FROM tbSeries WHERE dia=:dia")
    fun seleccionarSeriesDia(dia:String):MutableList<Serie>
    /**
     * Funcion para contar las series que hay en un dia y de un ejercicio en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("SELECT COUNT(*) FROM tbSeries WHERE dia=:dia AND idEjercicio=:idEjercicio")
    fun numeroSeries(dia:String,idEjercicio: Int):Int
    /**
     * Funcion para actualizar el RPE de una serie en un dia y de un ejercicio en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("UPDATE tbSeries SET RPE=:RPE WHERE Dia=:dia AND IdEjercicio=:idEjercicio AND Id=:Id")
    fun actualizarRPE(RPE:Int,dia: String,idEjercicio: Int,Id:Int)
    /**
     * Funcion para actualizar el peso de una serie en un dia y de un ejercicio en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("UPDATE tbSeries SET Peso=:peso WHERE Dia=:dia AND IdEjercicio=:idEjercicio AND Id=:Id")
    fun actualizarPeso(peso:Int,dia: String,idEjercicio: Int,Id:Int)
    /**
     * Funcion para actualizar las repeticiones de una serie en un dia y de un ejercicio en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("UPDATE tbSeries SET Reps=:reps WHERE Dia=:dia AND IdEjercicio=:idEjercicio AND Id=:Id")
    fun actualizarReps(reps:Int,dia: String,idEjercicio: Int,Id:Int)
    /**
     * Funcion para seleccionar repeticiones RPE y peso de un dia y de un ejercicio en la base de datos
     *
     * @author Yeray Sardón Ibañez
     */
    @Query("SELECT tbSeries.Reps,tbSeries.Peso,tbSeries.RPE FROM tbSeries JOIN tbEjercicios ON tbSeries.Dia=tbEjercicios.Dia WHERE tbSeries.Dia=:dia AND tbEjercicios.Nombre=:ejercicio AND tbEjercicios.Modificaciones=:modificacion ORDER BY Reps ASC,Peso DESC,RPE DESC" )
    fun seriesPorEjYDia(ejercicio:String,modificacion:String,dia:String):List<DatosPR>

}