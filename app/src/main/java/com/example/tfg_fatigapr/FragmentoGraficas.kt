package com.example.tfg_fatigapr


import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceManager.*
import com.example.tfg_fatigapr.clasesDatos.Usuario
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
public class FragmentoGraficas : Fragment() {
    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type
    lateinit var usuario:Usuario
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_graficas, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lineChartView = view.findViewById<LineChart>(R.id.linechart)
        /*val diaActual=if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MM-yyyy").toString()
        }else{*/
            var date = Date()
            val formatter = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
        val diaActual=formatter.format(date)
        //}
        val sharedPreferences: SharedPreferences =
            getDefaultSharedPreferences(context)
        if(context!=null) {
            val db= RoomDataBase.getInstance(context!!)!!
            val st=getString(R.string.key_editpref_nombre)
            val str=sharedPreferences.getString(st,"")!!
            usuario = db.usuariosDAO().seleccionarusuario(str)

        }
        val xVal= arrayListOf<String>(cambiarDia(diaActual,-2),cambiarDia(diaActual,-1),diaActual,cambiarDia(diaActual,1))
        var lineDataSet = LineDataSet(dataValues(xVal), "Volumen")
        var datasets = ArrayList<ILineDataSet>()
        datasets.add(lineDataSet)
        var data = LineData(datasets)
        lineChartView.data = data
        lineChartView.invalidate()
        var xAxis=lineChartView.xAxis
        xAxis.position=XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity=1f
        xAxis.valueFormatter=IndexAxisValueFormatter(xVal)
    }
    private fun dataValues(xVal:ArrayList<String>): ArrayList<Entry> {
        var dataset = ArrayList<Entry>()
        val d =calcularVolumendelDia(xVal[0])
        dataset.add(Entry(0f, calcularVolumendelDia(xVal[0])))
        dataset.add(Entry(1f, calcularVolumendelDia(xVal[1])))
        dataset.add(Entry(2f, calcularVolumendelDia(xVal[2])))
        dataset.add(Entry(3f, calcularVolumendelDia(xVal[3])))
        return dataset
    }

    fun cambiarDia(dia:String,offset:Int):String{
        /*val d= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(dia) as Date
        } else {*/
            val dateFormat = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
            val d=dateFormat.parse(dia)
        //}
        val c=Calendar.getInstance()
        c.time=d
        c.add(Calendar.DATE,offset)
        val diaN=getString(R.string.formatodiames,c.get(Calendar.DATE).toString(),(c.get(Calendar.MONTH)+1).toString())//dayeundiamas.date.toString()+"-"+dayeundiamas.month.toString()+"-"+dayeundiamas..toString()
        return diaN
    }
    fun calcularVolumendelDia(dia:String):Float{
        var gson= Gson()
        val usuarios:List<Usuario>
        var jsonPrueba=retjson()
        val tipoUsurio = genericType<List<Usuario>>()
        var volumenDia:Float=0f
        //usuarios = Gson().fromJson<List<Usuario>>(jsonPrueba, tipoUsurio)
        for (d in usuario.dia) {
            val diaEntero=d.id.split("-")
            val diaEntero1=dia.split("-")
            if ((diaEntero[0] == diaEntero1[0])&&diaEntero[1] == diaEntero1[1]) {
                for(ej in d.ejercicios){
                    for(s in ej.series){
                        volumenDia+=s.peso*s.reps
                    }
                }
            }
        }
        return volumenDia
    }

    fun retjson():String{
        return "[" +
                "{" +
                "id:5dc68d22f5c6b0cd58eb6ad0," +
                "nombre:Leonor," +
                "peso:64," +
                "dia:[" +
                "{" +
                "id:24-11-2019,"+
                "ejercicios: [" +
                "{" +
                "nombre:Sentadilla," +
                "modificaciones: 0," +
                "series:["+
                "{"+
                "id: 0," +
                "peso: 14," +
                "RPE: 8," +
                "reps: 1"+
                "}," +
                "{" +
                "id: 1," +
                "peso: 96," +
                "RPE: 8," +
                "reps: 6" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 13," +
                "RPE: 1," +
                "reps: 2" +
                "}" +
                "]" +
                "}," +
                "{" +
                "nombre: 'Press Banca'," +
                "modificaciones: 1," +
                "series: [" +
                "{" +
                "id: 0," +
                "peso: 162," +
                "RPE: 5," +
                "reps: 3" +
                "}," +
                "{" +
                "id: 1," +
                "peso: 90,"+
                "RPE: 10," +
                "reps: 5" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 160," +
                "RPE: 8," +
                "reps: 5" +
                "}" +
                "]" +
                "}," +
                "{" +
                "nombre: 'Press Militar'," +
                "modificaciones: 2,"+
                "series: [" +
                "{" +
                "id: 0," +
                "peso: 199," +
                "RPE: 4," +
                "reps: 8" +
                "}," +
                "{" +
                "id: 1," +
                "peso: 37," +
                "RPE: 1," +
                "reps: 1" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 179," +
                "RPE: 1," +
                "reps: 1" +
                "}" +
                "]" +
                "}" +
                "]" +
                "}," +
                "{" +
                "id:22-11-2019,"+
                "ejercicios: [" +
                "{" +
                "nombre:Georgette," +
                "modificaciones: 0," +
                "series:["+
                "{"+
                "id: 0," +
                "peso: 14," +
                "RPE: 8," +
                "reps: 1"+
                "}," +
                "{" +
                "id: 1," +
                "peso: 96," +
                "RPE: 8," +
                "reps: 6" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 13," +
                "RPE: 1," +
                "reps: 2" +
                "}" +
                "]" +
                "}," +
                "{" +
                "nombre: Bowers," +
                "modificaciones: 1," +
                "series: [" +
                "{" +
                "id: 0," +
                "peso: 162," +
                "RPE: 5," +
                "reps: 3" +
                "}," +
                "{" +
                "id: 1," +
                "peso: 90,"+
                "RPE: 10," +
                "reps: 5" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 160," +
                "RPE: 8," +
                "reps: 5" +
                "}" +
                "]" +
                "}," +
                "{" +
                "nombre: Hancock," +
                "modificaciones: 2,"+
                "series: [" +
                "{" +
                "id: 0," +
                "peso: 199," +
                "RPE: 4," +
                "reps: 8" +
                "}," +
                "{" +
                "id: 1," +
                "peso: 37," +
                "RPE: 1," +
                "reps: 1" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 179," +
                "RPE: 1," +
                "reps: 1" +
                "}" +
                "]" +
                "}" +
                "]" +
                "}"+
                "]"+
                "}," +
                "{" +
                "id:5dc68d221cd4525112db348d," +
                "nombre:Walsh ," +
                "peso: 96," +
                "dia: [" +
                "{" +
                "id:22-11-2019," +
                "ejercicios: [" +
                "{" +
                "nombre: Mitzi," +
                "modificaciones: 0," +
                "series:["+
                "{"+
                "id: 0," +
                "peso: 92," +
                "RPE: 2," +
                "reps: 3" +
                "}," +
                "{" +
                "id: 1," +
                "peso: 14," +
                "RPE: 1," +
                "reps: 3" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 195," +
                "RPE: 7,"+
                "reps: 3" +
                "}" +
                "]" +
                "}," +
                "{" +
                "nombre:Schultz," +
                "modificaciones: 1," +
                "series: [" +
                "{" +
                "id: 0," +
                "peso: 14," +
                "RPE: 2," +
                "reps: 4"+
                "}," +
                "{" +
                "id: 1," +
                "peso: 167," +
                "RPE: 10," +
                "reps: 5" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 101," +
                "RPE: 5," +
                "reps: 2" +
                "}" +
                "]" +
                "}," +
                "{" +
                "nombre: Evelyn," +
                "modificaciones: 2," +
                "series: [" +
                "{" +
                "id: 0," +
                "peso: 195," +
                "RPE: 10," +
                "reps: 6" +
                "}," +
                "{" +
                "id: 1," +
                "peso: 153," +
                "RPE: 6," +
                "reps: 4" +
                "}," +
                "{" +
                "id: 2," +
                "peso: 146," +
                "RPE: 5," +
                "reps: 3" +
                "}" +
                "]" +
                "}" +
                "]" +
                "}" +
                "]"+
                "}]"
    }
}

