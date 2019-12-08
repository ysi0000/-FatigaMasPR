package com.example.tfg_fatigapr.clasesDatos

import androidx.room.*


@Entity(tableName = "tbDias")
data class Dia (

    @PrimaryKey(autoGenerate = false)
    val id:String,
    @ColumnInfo(name = "Ejercicios")
    @TypeConverters(ConversorEjericicio::class)
    val ejercicios:List<Ejercicio>
)