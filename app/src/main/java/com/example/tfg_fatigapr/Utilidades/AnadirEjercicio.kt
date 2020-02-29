package com.example.tfg_fatigapr.Utilidades

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.ViewModels.ViewModelEjercicios
import com.example.tfg_fatigapr.ViewModels.ViewModelEjerciciosFactory
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import com.example.tfg_fatigapr.databinding.ActivityAnadirEjercicioBinding


class AnadirEjercicio : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var dia:String
    private lateinit var binding:ActivityAnadirEjercicioBinding
    private var nomEjercicio:String=""
    private var nomModificacion=arrayOf("Barra","Ninguno","Estandar","Ninguno","Estandar","Estandar")
    private lateinit var viewModel: ViewModelEjercicios
    private lateinit var ejercicios:Array<String>
    private lateinit var tironVertical:Array<String>
    private lateinit var tironHorizontal:Array<String>
    private lateinit var empujeVertical:Array<String>
    private lateinit var empujeHorizontal:Array<String>
    private lateinit var dominantesDeRodilla:Array<String>
    private lateinit var dominantesDeCadera:Array<String>
    private lateinit var tipoBarra:Array<String>
    private lateinit var cargaAcumulativa:Array<String>
    private lateinit var recorrido:Array<String>
    private lateinit var equipamiento:Array<String>
    private lateinit var tempo:Array<String>
    private lateinit var agarre:Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_ejercicio)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        dia=intent.getStringExtra("dia")!!
        viewModel= ViewModelProvider(this,
            ViewModelEjerciciosFactory(this)
        ).get(ViewModelEjercicios::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_anadir_ejercicio)

        ejercicios =
            arrayOf("Tiron Vertical","Tiron Horizontal","Empuje Vertical","Empuje Horizontal","Dominantes de Rodilla","Dominantes de Cadera")
        tironVertical=arrayOf("Dominadas","Jalones")
        tironHorizontal=arrayOf("Remo Pendlay","Remo en Barra","Remo en BarraT","Remo Sentado con cable")
        empujeVertical=arrayOf("Fondos","Press Militar","Press Militar Sentado","Push Press")
        empujeHorizontal= arrayOf("Press Banca","Aperturas","Press desde el Suelo","Press Inclinado")
        dominantesDeRodilla=arrayOf("Sentadilla","Sentadilla bulgara","Zancadas","Jaca","Extensiones","Prensa","Curl Insquitibliales")
        dominantesDeCadera= arrayOf("Peso Muerto Convencional","Peso Muerto Sumo","Buenos Dias","Hip Thrust","Peso Muerto Rumano","Peso Muerto Piernas Semirrigidas")
        tipoBarra= arrayOf("Barra","Mancuernas","SSB","Hexagonal")
        cargaAcumulativa= arrayOf("Ninguna","Cadenas","Bandas")
        recorrido= arrayOf("Estandar","Pines Bajos","Pines Altos","Pines Medios","Deficit","Cajon","Tabla")
        equipamiento= arrayOf("Ninguno","Cinturon","Rodilleras")
        tempo= arrayOf("Estandar","Touch & Go","2ct Parada","3ct Parada","Tempo 600","Tempo 320","Tempo 530","Tempo 303","5ct Parada","7ct Parada")
        agarre= arrayOf("Estandar","Barra Alta","Barra Baja","Agarre Ancho","Agarre Estrecho","Agarre Snach")
    }


    fun seleccionarEjercicio(view:View){
        val builderEjercicio: AlertDialog.Builder = AlertDialog.Builder(this)
        builderEjercicio.setTitle("Elige un ejercicio")
        builderEjercicio.setIcon(R.drawable.ic_fitness_center_black_10dp)
        val builderModificaciones: AlertDialog.Builder = AlertDialog.Builder(this)
        builderModificaciones.setTitle("Elige una modificacion")
        builderEjercicio.setItems(ejercicios) { _, ejercicio ->
            // the user clicked on colors[which]
            binding.btNuevoEj.text=ejercicios[ejercicio]
            when(ejercicio){
                0->{builderModificaciones.setItems(tironVertical) { _, opciontironVertical ->
                    // the user clicked on colors[which]
                    binding.btNuevoEj.text=tironVertical[opciontironVertical]
                    nomEjercicio=tironVertical[opciontironVertical]
                    }
                    builderModificaciones.show()}
                1->{builderModificaciones.setItems(tironHorizontal) { _, opciontironHorizontal ->
                    // the user clicked on colors[which]
                    binding.btNuevoEj.text=tironHorizontal[opciontironHorizontal]
                    nomEjercicio=tironHorizontal[opciontironHorizontal]
                }
                    builderModificaciones.show()}
                2->{builderModificaciones.setItems(empujeVertical) { _, opcionempujeVertical ->
                    // the user clicked on colors[which]
                    binding.btNuevoEj.text=empujeVertical[opcionempujeVertical]
                    nomEjercicio=empujeVertical[opcionempujeVertical]
                }
                    builderModificaciones.show()}
                3->{builderModificaciones.setItems(empujeHorizontal) { _, opcionempujeHorizontal ->
                    // the user clicked on colors[which]
                    binding.btNuevoEj.text=empujeHorizontal[opcionempujeHorizontal]
                    nomEjercicio=empujeHorizontal[opcionempujeHorizontal]}
                    builderModificaciones.show()}
                4->{builderModificaciones.setItems(dominantesDeRodilla) { _, opciondominantesDeRodilla ->
                    // the user clicked on colors[which]
                    binding.btNuevoEj.text=dominantesDeRodilla[opciondominantesDeRodilla]
                    nomEjercicio=dominantesDeRodilla[opciondominantesDeRodilla]
                }
                    builderModificaciones.show()}
                5->{builderModificaciones.setItems(dominantesDeCadera) { _, opciondominantesDeCadera ->
                    // the user clicked on colors[which]
                    binding.btNuevoEj.text=dominantesDeCadera[opciondominantesDeCadera]
                    nomEjercicio=dominantesDeCadera[opciondominantesDeCadera]
                }
                    builderModificaciones.show()}
            }
        }
        builderEjercicio.show()

    }

    fun seleccionarTipoBarra(view:View) {
        val builderTipoBarra: AlertDialog.Builder = AlertDialog.Builder(this)
        builderTipoBarra.setTitle("Elige el tipo de barra")
        builderTipoBarra.setItems(tipoBarra) { _, tipobarra ->
            binding.btTipoBarra.text=tipoBarra[tipobarra]
            nomModificacion[0]=tipoBarra[tipobarra]
        }
        builderTipoBarra.show()

    }

    fun seleccionarCargaAcumulativa(view:View) {
        val builderCargaAcumulativa: AlertDialog.Builder = AlertDialog.Builder(this)
        builderCargaAcumulativa.setTitle("Elige acumulación de carga")
        builderCargaAcumulativa.setItems(cargaAcumulativa) { _, cargaacumulativa ->
            binding.btCargaAcumulativa.text=cargaAcumulativa[cargaacumulativa]
            nomModificacion[1]=cargaAcumulativa[cargaacumulativa]
        }
        builderCargaAcumulativa.show()

    }

    fun seleccionarRecorrido(view:View) {
        val builderRecorrido: AlertDialog.Builder = AlertDialog.Builder(this)
        builderRecorrido.setTitle("Elige el el recorrido del ejercicio")
        builderRecorrido.setItems(recorrido) { _, tipoRecorrido ->
            binding.btRecorrido.text=recorrido[tipoRecorrido]
            nomModificacion[2]=recorrido[tipoRecorrido]
        }
        builderRecorrido.show()

    }

    fun seleccionarEquipamiento(view:View) {
        val builderEquipamiento: AlertDialog.Builder = AlertDialog.Builder(this)
        builderEquipamiento.setTitle("Elige el tipo de equipamiento")
        builderEquipamiento.setItems(equipamiento) { _, tipoequipamiento ->
            binding.btEquipamiento.text=equipamiento[tipoequipamiento]
            nomModificacion[3]=equipamiento[tipoequipamiento]
        }
        builderEquipamiento.show()

    }

    fun seleccionarTempo(view:View) {
        val builderTempo: AlertDialog.Builder = AlertDialog.Builder(this)
        builderTempo.setTitle("Elige el tempo del ejercicio")
        builderTempo.setItems(tempo) { _, tipotempo ->
            binding.btTempo.text=tempo[tipotempo]
            nomModificacion[4]=tempo[tipotempo]
        }
        builderTempo.show()

    }

    fun seleccionarAgarre(view:View) {
        val builderAgarre: AlertDialog.Builder = AlertDialog.Builder(this)
        builderAgarre.setTitle("Elige el tipo de agarre")
        builderAgarre.setItems(agarre) { _, tipoagarre ->
            binding.btAgarre.text=agarre[tipoagarre]
            nomModificacion[5]=agarre[tipoagarre]
        }
        builderAgarre.show()

    }

        fun anadirEj(view:View) {
            if(nomEjercicio!="") {
                viewModel.insertarEjercicio(
                    Ejercicio(
                        viewModel.ejerciciosDia(dia),
                        nomEjercicio,
                        nombreModificacion(),
                        dia
                    )
                )
                finish()
            }
            else
                Toast.makeText(this,"Selecciona un ejercicio primero",Toast.LENGTH_LONG).show()

    }

    fun nombreModificacion(): String {
        var nombreMod=""
        if(nomModificacion[0] != "Barra")
            nombreMod+=nomModificacion[0]+" "
        if(nomModificacion[1]!="Ninguno")
            nombreMod+=nomModificacion[1]+" "
        if(nomModificacion[2]!="Estandar")
            nombreMod+=nomModificacion[2]+" "
        if(nomModificacion[4]!="Estandar")
            nombreMod+=nomModificacion[4]+" "
        if(nomModificacion[5]!="Estandar")
            nombreMod+=nomModificacion[4]+" "
        return nombreMod
    }
}
