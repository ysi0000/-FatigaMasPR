package com.example.tfg_fatigapr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class AnadirEjercicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_ejercicio)
        val nombreEjercicio=findViewById<EditText>(R.id.editText_aejercicio)

    }

    fun añadirEj(view: View){
        val db = RoomDataBase.getInstance(this)
        var usuario=db?.usuariosDAO()!!.seleccionarUsuarios()
        finish()
    }
}
