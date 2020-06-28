package com.example.tfg_fatigapr.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * Patron fabrica para el viewModel dado que por defecto el viewmodel no lleva un parametro,
 * con el fin de añadir la aplicacion al viewmodel necesaria para el contexto de la aplicacion
 */
class ViewModelSeriesFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelSeries::class.java)) {
            return ViewModelSeries(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}