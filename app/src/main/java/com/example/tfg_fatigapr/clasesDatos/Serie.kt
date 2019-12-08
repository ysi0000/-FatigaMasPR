package com.example.tfg_fatigapr.clasesDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbSeries")
data class Serie(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name="Peso")
    val peso:Int,
    @ColumnInfo(name="RPE")
    val RPE:Int,
    @ColumnInfo(name = "Reps")
    val reps:Int
)