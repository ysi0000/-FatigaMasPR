package com.example.tfg_fatigapr.clasesDatos

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tbSeries",primaryKeys = ["Id","Dia","IdEjercicio"])
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