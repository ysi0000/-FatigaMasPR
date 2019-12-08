package com.example.tfg_fatigapr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.actividad_opciones.*


class ActividadOpciones : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_opciones)
        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        // Set the action bar back button to look like an up button
        // Set the action bar back button to look like an up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
