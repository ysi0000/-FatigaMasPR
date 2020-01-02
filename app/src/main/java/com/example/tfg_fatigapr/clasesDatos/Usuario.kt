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
    var dia:MutableList<Dia>

){
    override fun equals(other: Any?): Boolean {
        if(other is Usuario) {
            return this.nombre == other.nombre
        }

        return false
    }

    fun getDia(diaelegido:String): Dia? {
        var di:Dia?=null
        for(d in dia){
            if(d.id == diaelegido) {
                di=d
                break
            }
        }
        return di
    }

    fun addEjercicio(diaelegido:String,ejercicio:Ejercicio){
        for(d in dia){
            if(d.id == diaelegido) {
                d.ejercicios.add(ejercicio)//Delegar en subcalses
                break
            }
        }
    }

    fun addSerie(ejercicio: Ejercicio,diaelegido: String){
        for(d in dia){
            if(d.id == diaelegido) {
                for(e in d.ejercicios){
                    if(e.id==ejercicio.id){
                        e.series.add(Serie(d.ejercicios.size,0,0,0))
                    }
                }
            }
        }
    }

    fun cambiarPeso(diaelegido:String,ejercicio:String,id:Int,peso:Int){
        for(d in dia){
            if(d.id == diaelegido) {
                for(e in d.ejercicios){
                    if(e.nombre==ejercicio){
                        for(s in e.series){
                            if(s.id==id){
                                s.peso=peso
                            }
                        }
                    }
                }
            }
        }
    }
    fun cambiarRPE(diaelegido:String,ejercicio:String,id:Int,RPE:Int){
        for(d in dia){
            if(d.id == diaelegido) {
                for(e in d.ejercicios){
                    if(e.nombre==ejercicio){
                        for(s in e.series){
                            if(s.id==id){
                                s.RPE=RPE
                            }
                        }
                    }
                }
            }
        }
    }
    fun cambiarReps(diaelegido:String,ejercicio:String,id:Int,reps:Int){
        for(d in dia){
            if(d.id == diaelegido) {
                for(e in d.ejercicios){
                    if(e.nombre==ejercicio){
                        for(s in e.series){
                            if(s.id==id){
                                s.reps=reps
                            }
                        }
                    }
                }
            }
        }
    }
}

