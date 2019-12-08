package com.example.tfg_fatigapr.clasesDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "tbEjercicios")
data class Ejercicio(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name="Nombre")
    val nombre:String,
    @ColumnInfo(name="Modificaciones")
    val modificaciones:String,
    @ColumnInfo(name="Series")
    @TypeConverters(ConversorSerie::class)
    val series:List<Serie>
)

