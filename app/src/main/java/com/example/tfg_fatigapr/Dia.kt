package com.example.tfg_fatigapr

data class Dia (
    val id:String,
    val nombre:String,
    val modificaciones:String,
    val ejercicios:List<Ejercicio>
)