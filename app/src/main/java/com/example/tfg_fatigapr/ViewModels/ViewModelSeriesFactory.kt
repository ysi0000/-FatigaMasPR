package com.example.tfg_fatigapr.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelSeriesFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelSeries::class.java)) {
            return ViewModelSeries(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}