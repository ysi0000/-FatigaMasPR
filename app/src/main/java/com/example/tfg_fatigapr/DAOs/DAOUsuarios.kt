package com.example.tfg_fatigapr.DAOs

import androidx.room.*
import com.example.tfg_fatigapr.clasesDatos.Dia
import com.example.tfg_fatigapr.clasesDatos.Usuario


@Dao
interface DAOUsuarios {
    @Query("SELECT * FROM tbUsuarios")
    fun seleccionarUsuarios(): List<Usuario>

    @Insert
    fun nuevoUsuario(usuario: Usuario)

    @Delete
    fun eleiminarUsuario(usuario: Usuario)

    @Query("SELECT * FROM tbUsuarios WHERE Nombre=:nombre")
    fun seleccionarusuario(nombre:String):Usuario

    @Query("UPDATE tbUsuarios SET Nombre=:nombreNuevo WHERE Nombre=:nombreAntiguo")
    fun actualizarNombre(nombreNuevo:String,nombreAntiguo:String)

    @Query("UPDATE tbUsuarios SET Peso=:pesoNuevo WHERE Nombre=:nombre")
    fun actualizarPeso(pesoNuevo:Int,nombre: String)

    @Query("UPDATE tbUsuarios SET Dia=:dia WHERE Nombre=:nombre")
    fun actualizarDias(nombre:String,dia:MutableList<Dia>)

}