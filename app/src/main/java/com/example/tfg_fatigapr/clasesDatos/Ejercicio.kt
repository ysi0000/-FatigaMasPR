package com.example.tfg_fatigapr.clasesDatos

import androidx.room.ColumnInfo
import androidx.room.Entity


/**
 * Esta clase se utilza para albergar la informacion de los ejercicios
 * con anotaciones para el uso de Room
 */
@Entity(tableName = "tbEjercicios",primaryKeys = ["Id","Dia","Nombre_Usuario"])
data class Ejercicio(
    @ColumnInfo(name = "Id")
    var id:Int,
    @ColumnInfo(name="Nombre")
    val nombre:String,
    @ColumnInfo(name="Modificaciones")
    val modificaciones:String,
    @ColumnInfo(name="Dia")
    val dia:String,
    @ColumnInfo(name="Nombre_Usuario")
    val nombreUsuario:String
)

