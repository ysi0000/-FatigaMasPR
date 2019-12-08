package com.example.tfg_fatigapr.clasesDatos

import androidx.room.*
import com.example.tfg_fatigapr.clasesDatos.Dia

@Entity(tableName = "tbUsuarios",indices = [Index(value = ["Nombre"], unique = true)])
data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "Nombre")
    var nombre:String,
    @ColumnInfo(name="Peso")
    var peso:Int,
    @ColumnInfo(name="Dia")
    @TypeConverters(ConversorDia::class)
    var dia:List<Dia>

){
    override fun equals(other: Any?): Boolean {
        if(other is Usuario) {
            return this.nombre == other.nombre
        }

        return false
    }
}

