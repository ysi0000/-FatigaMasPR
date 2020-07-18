package com.example.tfg_fatigapr.clasesDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

/**
 * Esta clase se utilza para albergar la informacion de las series
 * con anotaciones para el uso de Room
 */
@Entity(tableName = "tbSeries",
    primaryKeys = ["Id","Dia","IdEjercicio"]


    )
    data class Serie(
        @ColumnInfo(name= "Id")
        var id:Int,
        @ColumnInfo(name="Peso")
        var peso:Int,
        @ColumnInfo(name="RPE")
        var RPE:Int,
        @ColumnInfo(name = "Reps")
        var reps:Int,
        @ColumnInfo(name = "Dia")
        var dia:String,
        @ColumnInfo(name = "IdEjercicio")
        var idEjercicio:Int
    )