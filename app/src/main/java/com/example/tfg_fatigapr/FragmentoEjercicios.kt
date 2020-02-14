package com.example.tfg_fatigapr


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*


class FragmentoEjercicios : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var db:RoomDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.content_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val botonAtras = view.findViewById<Button>(R.id.bt_diaAnterior)
            val botonAdelante = view.findViewById<Button>(R.id.bt_diaPosterior)
            val botonAnadirEjercicio = view.findViewById<Button>(R.id.bt_anadirEjercicio)
            val diaActual = view.findViewById<TextView>(R.id.dia)
            val selectorDia = view.findViewById<DatePicker>(R.id.selectorEjercicio)
                if (context != null) {
                    db = RoomDataBase.getInstance(context!!)!!
                }

                //region Listeners botones
                botonAtras.setOnClickListener {
                    val dia = view.findViewById<TextView>(R.id.dia)
                    //Quitado el if por un fallo en LocalDate ->Probar con otros SDK
                    //val d= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //  LocalDate.parse(dia.text.toString()) as Date
                    //} else {
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
                    val d = dateFormat.parse(dia.text.toString())
                    //}
                    val c = Calendar.getInstance()
                    c.time = d!!
                    c.add(Calendar.DATE, -1)
                    dia.text = getString(
                        R.string.formatodiamesao,
                        c.get(Calendar.DATE).toString(),
                        (c.get(Calendar.MONTH) + 1).toString(),
                        c.get(Calendar.YEAR).toString()
                    )//c.get(Calendar.DATE).toString()+"-"+(c.get(Calendar.MONTH)+1).toString()+"-"+c.get(Calendar.YEAR).toString()//dayeundiamas.date.toString()+"-"+dayeundiamas.month.toString()+"-"+dayeundiamas..toString()
                    selectorDia.updateDate(
                        c.get(Calendar.YEAR),
                        (c.get(Calendar.MONTH) + 1),
                        c.get(Calendar.DATE)
                    )
                    cargarDia(dia, view)
                }

                botonAdelante.setOnClickListener {
                    val dia = view.findViewById<TextView>(R.id.dia)
                    //val d= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //  LocalDate.parse(dia.text.toString()) as Date
                    //} else {
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
                    val d = dateFormat.parse(dia.text.toString())
                    //}
                    val c = Calendar.getInstance()
                    c.time = d!!
                    c.add(Calendar.DATE, 1)
                    dia.text = getString(
                        R.string.formatodiamesao,
                        c.get(Calendar.DATE).toString(),
                        (c.get(Calendar.MONTH) + 1).toString(),
                        c.get(Calendar.YEAR).toString()
                    )//dayeundiamas.date.toString()+"-"+dayeundiamas.month.toString()+"-"+dayeundiamas..toString()
                    selectorDia.updateDate(
                        c.get(Calendar.YEAR),
                        (c.get(Calendar.MONTH) + 1),
                        c.get(Calendar.DATE)
                    )
                    cargarDia(dia, view)
                }

                botonAnadirEjercicio.setOnClickListener {
                    val intentAnadirEjercicio = Intent(context, AnadirEjercicio::class.java)
                    intentAnadirEjercicio.putExtra(
                        "dia",
                        view.findViewById<TextView>(R.id.dia).text.toString()
                    )
                    startActivityForResult(intentAnadirEjercicio, 1)
                }

                diaActual.setOnClickListener {
                    if (selectorDia.visibility == View.VISIBLE) {
                        val dia = selectorDia.dayOfMonth
                        val mes = selectorDia.month
                        val anno = selectorDia.year
                        (it as TextView).text = getString(
                            R.string.formatodiamesao,
                            dia.toString(),
                            mes.toString(),
                            anno.toString()
                        )
                        cargarDia(it, view)
                        selectorDia.visibility = View.GONE

                    } else
                        selectorDia.visibility = View.VISIBLE
                }
                //endregion
                val dia = view.findViewById<TextView>(R.id.dia)
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //DateTimeFormatter.ofPattern("dd-MM-yyyy").toString()
                //}else{
                val date = Date()
                val formatter = SimpleDateFormat("d-M-yyyy", Locale.FRANCE)
                dia.text = formatter.format(date)
                //}
                cargarDia(dia, view)
                Log.d("TAG", "TAG ")

    }



    private fun cargarDia(dia:TextView, view:View) {
        val ejercicios:List<Ejercicio> =db.ejercicioDAO().seleccionarEjercicios(dia.text.toString())
        viewAdapter = AdaptadorEjercicios(ejercicios)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == 1  && resultCode  == 0) {
                cargarDia(dia,view!!)

            }
        } catch (ex:Exception) {

        }

    }
}
