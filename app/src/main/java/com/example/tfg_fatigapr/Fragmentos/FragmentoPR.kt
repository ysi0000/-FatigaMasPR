package com.example.tfg_fatigapr.Fragmentos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.ViewModels.ViewModelEjercicios
import com.example.tfg_fatigapr.ViewModels.ViewModelEjerciciosFactory
import com.example.tfg_fatigapr.ViewModels.ViewModelSeries
import com.example.tfg_fatigapr.ViewModels.ViewModelSeriesFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FragmentoPR :Fragment() {

    private lateinit var viewModelEjercicios: ViewModelEjercicios
    private lateinit var viewModelSerie: ViewModelSeries
    private lateinit var listaPREstimadas:List<List<Int>>
    private lateinit var dias:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelEjercicios = ViewModelProvider(
            this,
            ViewModelEjerciciosFactory(
                context!!
            )
        ).get(ViewModelEjercicios::class.java)
        viewModelSerie = ViewModelProvider(
            this,
            ViewModelSeriesFactory(context!!)
        ).get(ViewModelSeries::class.java)
        return inflater.inflate(R.layout.fragmento_pr, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inicializarLista()
        val lineChartView = view.findViewById<LineChart>(R.id.lineChartPR)
        lineChartView.description.isEnabled=false
        dias= arrayListOf()
        val date = Date()
        val formatter = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val diaActual=formatter.format(date)

        for(i in 25 downTo 1){
            dias.add(cambiarDia(diaActual,-i))
        }
        dias.add(diaActual)
        dias.add(cambiarDia(diaActual,1))
        val xVal= dias
        val lineDataSet = LineDataSet(dataValues(xVal), "Volumen")
        val datasets = ArrayList<ILineDataSet>()
        lineDataSet.color= Color.RED
        lineDataSet.lineWidth=4f
        datasets.add(lineDataSet)
        val data = LineData(datasets)
        lineChartView.data = data
        lineChartView.invalidate()
        lineChartView.axisLeft.axisMinimum=0f
        lineChartView.axisRight.axisMinimum=0f
        val xAxis=lineChartView.xAxis
        xAxis.position= XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity=1f
        xAxis.valueFormatter= IndexAxisValueFormatter(xVal)
        val yAxis=lineChartView.axisLeft
        yAxis.granularity=200f

    }
    fun inicializarLista(){
        listaPREstimadas= listOf(listOf(65,88,85,82,80,77,75,72,69,67,64),listOf(70,89,86,84,81,79,76,74,71,68,65),
            listOf(75,91,88,85,82,80,77,75,72,69,67),listOf(80,92,89,86,84,81,79,76,74,71,68),
            listOf(85,94,91,88,85,82,80,77,75,72,69),listOf(90,96,92,89,86,84,81,79,76,74,71),
            listOf(95,98,94,91,88,85,82,80,77,75,72),listOf(100,100,96,92,89,86,84,81,79,76,74))
    }
    private fun dataValues(xVal:ArrayList<String>): ArrayList<Entry> {
        val dataSet = ArrayList<Entry>()
        for(i in 1..26){
            var calculoPR=calcularVolumenDia(xVal[i])
            if(calculoPR!=0f)
                dataSet.add(Entry(i.toFloat(),calculoPR ))
        }
        return dataSet
    }
    private fun calcularVolumenDia(dia:String):Float{
        var volumenDia=0f
        val serie=viewModelSerie.seriesPorEjYDia("Banca","2ct Parada",dia)
        return if(serie!=null)
            round(serie.Peso/(elegirPorcentaje(serie.RPE,serie.Reps)/100f),2.5f)
        else 0f
    }
    private fun cambiarDia(dia:String, offset:Int):String{
        val dateFormat = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val d=dateFormat.parse(dia)
        val c= Calendar.getInstance()
        c.time=d!!
        c.add(Calendar.DATE,offset)
        return getString(R.string.formatodiamesao,c.get(Calendar.DATE).toString(),(c.get(Calendar.MONTH)+1).toString(),c.get(
            Calendar.YEAR).toString())
    }
    fun elegirPorcentaje(RPE:Int,Reps:Int): Int {
        return when(RPE){
            10->listaPREstimadas[7][Reps]
            9->listaPREstimadas[5][Reps]
            8->listaPREstimadas[3][Reps]
            else->listaPREstimadas[1][Reps]
        }
    }
    fun round(i: Float, v: Float): Float {
        return ((Math.round(i / v) * v))
    }
    }

class DatosPR(
    var Reps:Int,
    var Peso:Int,
    var RPE:Int
)
