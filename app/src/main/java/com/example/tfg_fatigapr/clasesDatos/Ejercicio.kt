package com.example.tfg_fatigapr.clasesDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "tbEjercicios",primaryKeys = ["Id","Dia"])
data class Ejercicio(
    @ColumnInfo(name = "Id")
    var id:Int,
    @ColumnInfo(name="Nombre")
    val nombre:String,
    @ColumnInfo(name="Modificaciones")
    val modificaciones:String,
    @ColumnInfo(name="Dia")
    val dia:String
)

