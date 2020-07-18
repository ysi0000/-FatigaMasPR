package com.example.tfg_fatigapr.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tfg_fatigapr.clasesDatos.Ejercicio

/**
 * Interfaz para realizar operaciones con la base de datos
 *
 * @author Yeray Sardon Ibañez
 */
@Dao
interface DAOEjercicios {
    /**
     * Funcion para seleccionar los ejercicios de un usuario
     *
     * @author Yeray Sardon Ibañez
     */
    @Query("SELECT * FROM tbEjercicios WHERE Nombre_Usuario=:nombreUsuario")
    fun seleccionarEjercicios(nombreUsuario: String):List<Ejercicio>
    /**
     * Funcion para añadir los ejercicios a un usuario
     *
     * @author Yeray Sardon Ibañez
     */
    @Insert
    fun añadirEjercicio(ejercicio: Ejercicio)
    /**
     * Funcion para eliminar los ejercicios de un usuario
     *
     * @author Yeray Sardon Ibañez
     */
    @Delete
    fun eliminarEjercicio(Ejercicio: Ejercicio)
    /**
     * Funcion para seleccionar los ejercicios de un usuario en un dia
     *
     * @author Yeray Sardon Ibañez
     */
    @Query("SELECT * FROM tbEjercicios WHERE Dia=:dia AND Nombre_Usuario=:nombreUsuario")
    fun seleccionarEjercicios(dia:String,nombreUsuario: String):List<Ejercicio>
    /**
     * Funcion para contar los ejercicios de un usuario en un dia
     *
     * @author Yeray Sardon Ibañez
     */
    @Query("SELECT COUNT(*) FROM tbEjercicios WHERE Dia=:dia AND Nombre_Usuario=:nombreUsuario")
    fun ejerciciosDia(dia:String,nombreUsuario: String):Int
    /**
     * Funcion para añadir los ejercicios a un usuario
     *
     * @author Yeray Sardon Ibañez
     */
    @Query("SELECT * FROM tbEjercicios WHERE Nombre_Usuario=:nombreUsuario AND Id=:idEj")
    fun seleccionarEjercicio(nombreUsuario: String,idEj:Int):List<Ejercicio>


}