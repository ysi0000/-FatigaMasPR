package com.example.tfg_fatigapr.clasesDatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbSeries")
data class Serie(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name="Peso")
    var peso:Int,
    @ColumnInfo(name="RPE")
    var RPE:Int,
    @ColumnInfo(name = "Reps")
    var reps:Int
)