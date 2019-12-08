package com.example.tfg_fatigapr


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.clasesDatos.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentoEjercicios : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.content_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var gson= Gson()
        val usuarios:List<Usuario>
        val botonatras=view.findViewById<Button>(R.id.bt_diaAnterior)
        val botonalante=view.findViewById<Button>(R.id.bt_diaPosterior)
        val botonañadirEjercicio=view.findViewById<Button>(R.id.bt_anadirEjercicio)
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity!!.applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            // Permission has already been granted
            /*var gson=Gson()
            var f=File("/sdcard/Download/file.json").readText()*/
            /* val bufferedReader: BufferedReader = f.bufferedReader()
             val inputString = bufferedReader.use { it.readText() }*/
            var jsonPrueba=retjson()

            try {

                /*val turnsType = object : TypeToken<List<Usuario>>() {}.type
                var post= gson.fromJson<List<Usuario>>(inputString, turnsType)*/
                if(context!=null) {
                    val db = RoomDataBase.getInstance(context!!)
                    var usuario = db?.usuariosDAO()!!.seleccionarusuario("Yeray")

                }
                val tipoUsurio = genericType<List<Usuario>>()
                usuarios = Gson().fromJson<List<Usuario>>(jsonPrueba, tipoUsurio)

                //region Listeners botones
                botonatras.setOnClickListener{
                    val dia=view.findViewById<TextView>(R.id.dia)
                    val d= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDate.parse(dia.text.toString()) as Date
                    } else {
                        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
                        dateFormat.parse(dia.text.toString())
                    }
                    val c=Calendar.getInstance()
                    c.time=d
                    c.add(Calendar.DATE,-1)
                    dia.text=getString(R.string.formatodiamesao,c.get(Calendar.DATE).toString(),(c.get(Calendar.MONTH)+1).toString(),c.get(Calendar.YEAR).toString())//c.get(Calendar.DATE).toString()+"-"+(c.get(Calendar.MONTH)+1).toString()+"-"+c.get(Calendar.YEAR).toString()//dayeundiamas.date.toString()+"-"+dayeundiamas.month.toString()+"-"+dayeundiamas..toString()
                    cargarDia(usuarios[0],dia,view)
                }

                botonalante.setOnClickListener{
                    val dia=view.findViewById<TextView>(R.id.dia)
                    val d= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDate.parse(dia.text.toString()) as Date
                    } else {
                        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
                        dateFormat.parse(dia.text.toString())
                    }
                    val c=Calendar.getInstance()
                    c.time=d
                    c.add(Calendar.DATE,1)
                    dia.text=getString(R.string.formatodiamesao,c.get(Calendar.DATE).toString(),(c.get(Calendar.MONTH)+1).toString(),c.get(Calendar.YEAR).toString())//dayeundiamas.date.toString()+"-"+dayeundiamas.month.toString()+"-"+dayeundiamas..toString()
                    cargarDia(usuarios[0],dia,view)
                }

                botonañadirEjercicio.setOnClickListener{
                    var intentAñadirEjercicio= Intent(context,AnadirEjercicio::class.java)
                    startActivityForResult(intentAñadirEjercicio,1)
                }
                //endregion
                val dia=view.findViewById<TextView>(R.id.dia)
                dia.text =if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter.ofPattern("dd-MM-yyyy").toString()
                }else{
                    var date = Date()
                    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
                    formatter.format(date)
                }
                cargarDia(usuarios[0],dia,view)



                cargarDia(usuarios[0],dia,view)
                Log.d("TAG","TAG "+usuarios[0].nombre)
            }catch (e:Exception){
                Log.d("TAG","Error json")
            }
        }

        val permissionCheck = ContextCompat.checkSelfPermission(
            activity!!.applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    }


    fun cargarDia(usuario: Usuario, dia:TextView, view:View) {
        var diaencontrado = false
        for (d in usuario.dia) {
            if (d.id == dia.text.toString()) {
                viewAdapter = AdaptadorEjercicios(d.ejercicios)
                viewManager = LinearLayoutManager(context)
                recyclerView = view.findViewById<RecyclerView>(R.id.recycler_ejercicios).apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = viewManager

                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                }
                diaencontrado = true
                break
            }
        }
        if (!diaencontrado) {
            viewAdapter = AdaptadorEjercicios(emptyList())
            viewManager = LinearLayoutManager(context)
            recyclerView = view.findViewById<RecyclerView>(R.id.recycler_ejercicios).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }
        }


    }

    fun retjson():String{
        return "[" +
                "{" +
                    "id:5dc68d22f5c6b0cd58eb6ad0," +
                    "nombre:Leonor," +
                    "peso:64," +
                    "dia:[" +
                    "{" +
                        "id:30-11-2019,"+
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
    fun retjson1():String{
        return "[{id:5dc68d22f5c6b0cd58eb6ad0,nombre:Leonor,peso:64,dia:[{id:2014-11-30,"+
                "nombre:Georgette,modificaciones: 0,ejercicios: [{id: 0,peso: 14,RPE: 8,reps: 1"+
                "},{id: 1,peso: 96,RPE: 8,reps: 6},{id: 2,peso: 13,RPE: 1,reps: 2}]},{id: 2015-01-06," +
                "nombre: Bowers,modificaciones: 1,ejercicios: [{id: 0,peso: 162,RPE: 5,reps: 3},{id: 1,peso: 90,"+
                "RPE: 10,reps: 5},{id: 2,peso: 160,RPE: 8,reps: 5}]},{id: 2015-03-11,nombre: Hancock,modificaciones: 2,"+
                "ejercicios: [{id: 0,peso: 199,RPE: 4,reps: 8},{id: 1,peso: 37,RPE: 1,reps: 1},{id: 2,peso: 179,RPE: 1,reps: 1}]}]}]"
    }
}