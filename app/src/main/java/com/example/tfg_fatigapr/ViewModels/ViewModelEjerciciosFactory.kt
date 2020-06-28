package com.example.tfg_fatigapr.ViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Patron fabrica para el viewModel dado que por defecto el viewmodel no lleva un parametro,
 * con el fin de añadir la aplicacion al viewmodel necesaria para el contexto de la aplicacion
 */
class ViewModelEjerciciosFactory(val application: Application) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelEjercicios::class.java)) {
            return ViewModelEjercicios(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}