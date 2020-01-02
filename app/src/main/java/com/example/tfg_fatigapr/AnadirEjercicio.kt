package com.example.tfg_fatigapr

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.preference.PreferenceManager
import com.example.tfg_fatigapr.clasesDatos.Dia
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import com.example.tfg_fatigapr.clasesDatos.Serie
import com.example.tfg_fatigapr.clasesDatos.Usuario
import kotlinx.android.synthetic.main.content_main.*

class AnadirEjercicio : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var usuario: Usuario
    lateinit var dia:String
    lateinit var nombreEjercicio:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_ejercicio)
        nombreEjercicio=findViewById(R.id.editText_aejercicio)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        dia=intent.getStringExtra("dia")!!

    }

    fun añadirEj(view: View){
        val db= RoomDataBase.getInstance(this)!!
        val st=getString(R.string.key_editpref_nombre)
        val str=sharedPreferences.getString(st,"")!!
        val usuarioDao=db.usuariosDAO()
        usuario = usuarioDao.seleccionarusuario(str)
        val diaEncontrado=usuario.getDia(dia)
        if(diaEncontrado!=null){
            val dias=usuario.addEjercicio(dia, Ejercicio(0,nombreEjercicio.text.toString(),"",
                mutableListOf(Serie(0,0,0,0)))
            )

        }else{
            usuario.dia.add(
                Dia(dia, mutableListOf(Ejercicio(0,nombreEjercicio.text.toString(),"",
                mutableListOf(Serie(0,0,0,0)))))
            )

        }
        usuarioDao.actualizarDias(usuario.nombre,usuario.dia)
        finish()
    }
}
