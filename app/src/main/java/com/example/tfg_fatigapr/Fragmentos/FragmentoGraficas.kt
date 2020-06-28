package com.example.tfg_fatigapr.Fragmentos


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.Utilidades.RoomDataBase
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
    private lateinit var db: RoomDataBase
    private lateinit var dias:ArrayList<String>
    /**
     * Esta funcion se llama cada vez que se crea el fragmento
     * En el se une el layout con la logica del fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_graficas, container, false)

    }

    /**
     * Esta funcion se llama cuando el fragmento se ha creado en ella se instancia el grafico y
     * variables necesarias para rellenar el grafico
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lineChartView = view.findViewById<LineChart>(R.id.linechart)
        lineChartView.description.isEnabled=false
            val date = Date()
            val formatter = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val diaActual=formatter.format(date)
        if(context!=null) {
            db= RoomDataBase.getInstance(
                context!!
            )!!
        }
        dias= arrayListOf()
        for(i in 6 downTo 1){
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
        xAxis.position=XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity=1f
        xAxis.valueFormatter=IndexAxisValueFormatter(xVal)
        val yAxis=lineChartView.axisLeft
        yAxis.granularity=200f
    }

    /**
     * En esta funcion se inicializan los valores de la grafica
     */
    private fun dataValues(xVal:ArrayList<String>): ArrayList<Entry> {
        val dataSet = ArrayList<Entry>()
        for(i in 1..7){
            dataSet.add(Entry(i.toFloat(),calcularVolumenDia(xVal[i]) ))
        }
        return dataSet
    }

    /**
     * Esta es la funcion que se usa para rotar entre los dias anteriores
     * llama a la funcion calcularVolumen cada iteraccion
     */
    private fun cambiarDia(dia:String, offset:Int):String{
        val dateFormat = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val d=dateFormat.parse(dia)
        val c=Calendar.getInstance()
        c.time=d!!
        c.add(Calendar.DATE,offset)
        return getString(R.string.formatodiamesao,c.get(Calendar.DATE).toString(),(c.get(Calendar.MONTH)+1).toString(),c.get(Calendar.YEAR).toString())
    }

    /**
     * Esta funcion calcula el volumen en un dia especifico
     */
    private fun calcularVolumenDia(dia:String):Float{
        var volumenDia=0f
        val series=db.serieDAO().seleccionarSeriesDia(dia)
        for(serie:Serie in series){
            volumenDia+=(serie.peso*serie.reps)
        }
        return volumenDia
    }

}

