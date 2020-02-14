package com.example.tfg_fatigapr


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tfg_fatigapr.clasesDatos.Serie
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


class FragmentoGraficas : Fragment() {
    private lateinit var db:RoomDataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_graficas, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lineChartView = view.findViewById<LineChart>(R.id.linechart)
        lineChartView.description.isEnabled=false
            val date = Date()
            val formatter = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val diaActual=formatter.format(date)
        if(context!=null) {
            db= RoomDataBase.getInstance(context!!)!!
        }
        val xVal= arrayListOf<String>(cambiarDia(diaActual,-2),cambiarDia(diaActual,-1),diaActual,cambiarDia(diaActual,1))
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
        xAxis.position=XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity=1f
        xAxis.valueFormatter=IndexAxisValueFormatter(xVal)
        val yAxis=lineChartView.axisLeft
        yAxis.granularity=200f
    }
    private fun dataValues(xVal:ArrayList<String>): ArrayList<Entry> {
        val dataSet = ArrayList<Entry>()
        val dia1=calcularVolumenDia(xVal[0])
        val dia2=calcularVolumenDia(xVal[1])
        val dia3=calcularVolumenDia(xVal[2])
        val dia4=calcularVolumenDia(xVal[3])
        if(dia1!=0f)dataSet.add(Entry(0f,dia1 ))
        if(dia2!=0f) dataSet.add(Entry(1f, dia2))
        if(dia3!=0f)dataSet.add(Entry(2f, dia3))
        if(dia4!=0f)dataSet.add(Entry(3f, dia4))
        return dataSet
    }

    private fun cambiarDia(dia:String, offset:Int):String{
        val dateFormat = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val d=dateFormat.parse(dia)
        val c=Calendar.getInstance()
        c.time=d!!
        c.add(Calendar.DATE,offset)
        return getString(R.string.formatodiamesao,c.get(Calendar.DATE).toString(),(c.get(Calendar.MONTH)+1).toString(),c.get(Calendar.YEAR).toString())
    }
    private fun calcularVolumenDia(dia:String):Float{
        var volumenDia=0f
        val series=db.serieDAO().seleccionarSeriesDia(dia)
        for(serie:Serie in series){
            volumenDia+=(serie.peso*serie.reps)
        }
        return volumenDia
    }

}

