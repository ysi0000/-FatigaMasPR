package com.example.tfg_fatigapr.Fragmentos

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.ViewModels.ViewModelEjercicios
import com.example.tfg_fatigapr.ViewModels.ViewModelEjerciciosFactory
import com.example.tfg_fatigapr.ViewModels.ViewModelSeries
import com.example.tfg_fatigapr.ViewModels.ViewModelSeriesFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Fragmento de la opcion de RM estimada
 */
class FragmentoPR :Fragment() {

    private lateinit var viewModelEjercicios: ViewModelEjercicios
    private lateinit var viewModelSerie: ViewModelSeries
    private lateinit var listaPREstimadas:List<List<Int>>
    private lateinit var dias:ArrayList<String>
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var yAxis:YAxis
    private lateinit var xAxis:XAxis
    private var ejercicioSel="Ejercicio Press Banca Principal"
    private var error="Error"
    private lateinit var xVal:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelEjercicios = ViewModelProvider(
            this,
            ViewModelEjerciciosFactory(
                activity!!.application
            )
        ).get(ViewModelEjercicios::class.java)
        viewModelSerie = ViewModelProvider(
            this,
            ViewModelSeriesFactory(context!!)
        ).get(ViewModelSeries::class.java)
        return inflater.inflate(R.layout.fragmento_pr, container, false)
    }

    /**
     * Funcion que se llama cuando se crea la actividad
     * Se inicializa tanto la lista para calcular las PR
     * como todos los dias que se vayan a hacer en la grafica
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializarLista()
        val lineChartView = view.findViewById<LineChart>(R.id.lineChartPR)
        val selectorId=view.findViewById<Spinner>(R.id.selctorEj)
        lineChartView.description.isEnabled=false
        dias= arrayListOf()
        val date = Date()
        val formatter = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val diaActual=formatter.format(date)
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
        for(i in 25 downTo 1){
            dias.add(cambiarDia(diaActual,-i))
        }
        dias.add(diaActual)
        dias.add(cambiarDia(diaActual,1))
        construirGrafica(lineChartView)

        selectorId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (parent!!.getItemAtPosition(position).toString()) {
                    getString(R.string.ej_Sentadilla_principal) -> {
                        ejercicioSel=getString(R.string.ej_Sentadilla_principal)
                    }
                    getString(R.string.ej_Peso_Muerto_principal) -> {
                        ejercicioSel=getString(R.string.ej_Peso_Muerto_principal)
                    }
                    getString(R.string.ej_Press_Banca_principal) -> {
                        ejercicioSel=getString(R.string.ej_Press_Banca_principal)
                    }
                }
                val lineDataSet = LineDataSet(dataValues(xVal), "Volumen")
                val datasets = ArrayList<ILineDataSet>()
                datasets.add(lineDataSet)
                val data = LineData(datasets)
                lineChartView.data = data
                construirGrafica(lineChartView)
            }

        }

    }

    /**
     * Funcion que inicializa la lista
     */
    private fun inicializarLista(){
        listaPREstimadas= listOf(listOf(65,88,85,82,80,77,75,72,69,67,64),listOf(70,89,86,84,81,79,76,74,71,68,65),
            listOf(75,91,88,85,82,80,77,75,72,69,67),listOf(80,92,89,86,84,81,79,76,74,71,68),
            listOf(85,94,91,88,85,82,80,77,75,72,69),listOf(90,96,92,89,86,84,81,79,76,74,71),
            listOf(95,98,94,91,88,85,82,80,77,75,72),listOf(100,100,96,92,89,86,84,81,79,76,74))
    }

    /**
     * Funcion que recorre los 30 dias para calcular la pr del dia
     */
    private fun dataValues(xVal:ArrayList<String>): ArrayList<Entry> {
        val dataSet = ArrayList<Entry>()
        for(i in 1..26){
            val calculoPR=calcularVolumenDia(xVal[i])
            if(calculoPR!=0f)
                dataSet.add(Entry(i.toFloat(),calculoPR ))
        }
        return dataSet
    }
    private fun calcularVolumenDia(dia:String):Float{
        var serie:DatosPR?=null
        when(ejercicioSel){
            getString(R.string.ej_Press_Banca_principal)->{
                serie=viewModelSerie.seriesPorEjYDia("Press Banca",modificacionBanca(),dia)
            }
            getString(R.string.ej_Peso_Muerto_principal)->{
                if(sharedPreferences.getString("Peso Muerto",error)=="Peso Muerto Sumo"){
                    serie=viewModelSerie.seriesPorEjYDia("Peso Muerto Sumo",modificacionMuerto(),dia)
                }else {
                    serie = viewModelSerie.seriesPorEjYDia("Peso Muerto", modificacionMuerto(), dia)
                }
            }
            getString(R.string.ej_Sentadilla_principal)->{
                serie=viewModelSerie.seriesPorEjYDia("Sentadilla",modificacionSentadilla(),dia)
            }
        }

        return if(serie!=null)
            round(serie.Peso/(elegirPorcentaje(serie.RPE,serie.Reps)/100f),2.5f)
        else 0f
    }

    /**
     * Devuelve el dia siguiente al pedido
     */
    private fun cambiarDia(dia:String, offset:Int):String{
        val dateFormat = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val d=dateFormat.parse(dia)
        val c= Calendar.getInstance()
        c.time=d!!
        c.add(Calendar.DATE,offset)
        return getString(R.string.formatodiamesao,c.get(Calendar.DATE).toString(),(c.get(Calendar.MONTH)+1).toString(),c.get(
            Calendar.YEAR).toString())
    }

    /**
     *
     * Esta funcion escoje el porcentaje en la lista dependiendo de las repeticiones y RPE
     */
    private fun elegirPorcentaje(RPE:Int,Reps:Int): Int {
        return when(RPE){
            10->listaPREstimadas[7][Reps]
            9->listaPREstimadas[5][Reps]
            8->listaPREstimadas[3][Reps]
            else->listaPREstimadas[1][Reps]
        }
    }

    /**
     * Redondea un numero a un valor insertado
     */
    private fun round(i: Float, v: Float): Float {
        return ((Math.round(i / v) * v))
    }

    /**
     * Elige la modificacion de banca elegida en opciones
     */
    private fun modificacionBanca():String{
        return when(sharedPreferences.getString(getString(R.string.pref_Ejercicio_Banca),"Error")){
            "Press Banca 2ct Parada"->"2ct Parada "
            "TG Press Banca"->"Touch & Go"
            else->""
        }
    }
    /**
     * Elige la modificacion de sentadilla elegida en opciones
     */
    private fun modificacionSentadilla():String{
        return when(sharedPreferences.getString(getString(R.string.pref_Ejercicio_Sentadilla),error)){
            "Sentadilla 2ct Parada"->"2ct Parada "
            "Sentadilla Pines"->"Pines"
            else->""
        }
    }
    /**
     * Elige la modificacion de peso muerto elegida en opciones
     */
    private fun modificacionMuerto():String{
        return when(sharedPreferences.getString("Peso Muerto",error)){
            "Peso Muerto Sumo 2ct Parada"->"2ct Parada "
            "Peso Muerto Convencional 2ct Parada"->"2ct Parada "
            else->""
        }
    }

    /**
     * Construye la grafica y le da el diseño que se desea
     */
    private fun construirGrafica(lineChartView:LineChart){
        xVal= dias
        val lineDataSet = LineDataSet(dataValues(xVal), error)
        val datasets = ArrayList<ILineDataSet>()
        lineDataSet.color= Color.RED
        lineDataSet.lineWidth=4f
        datasets.add(lineDataSet)
        val data = LineData(datasets)
        lineChartView.data = data
        lineChartView.invalidate()
        lineChartView.axisLeft.axisMinimum=0f
        lineChartView.axisRight.axisMinimum=0f
        xAxis=lineChartView.xAxis
        xAxis.position= XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity=1f
        xAxis.valueFormatter= IndexAxisValueFormatter(xVal)
        yAxis=lineChartView.axisLeft
        yAxis.granularity=200f
    }
    }

/**
 * Clase de datos para guardar los datos y poder calcular de manera mas rapida
 */
class DatosPR(
    var Reps:Int,
    var Peso:Int,
    var RPE:Int
)
