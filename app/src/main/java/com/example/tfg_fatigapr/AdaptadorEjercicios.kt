package com.example.tfg_fatigapr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import com.example.tfg_fatigapr.clasesDatos.Serie


class AdaptadorEjercicios(private val myDataset: List<Ejercicio>) :
    RecyclerView.Adapter<AdaptadorEjercicios.MyViewHolder>() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var textView:TextView = view.findViewById(R.id.tv_ejercicio)
        var recyclerView:RecyclerView = view.findViewById(R.id.recycler_series)
        var anadirSerie:TextView=view.findViewById(R.id.tv_anadirSerie)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text=holder.textView.context.getString(R.string.nombre_ejercicio_modificaciones,
                                        myDataset[position].nombre,myDataset[position].modificaciones)
        val db=RoomDataBase.getInstance(holder.textView.context)
        val seriesDAO=db!!.serieDAO()
        val series=seriesDAO.seleccionarSeriesdeEjercicio(myDataset[position].dia,myDataset[position].id)




        viewManager = LinearLayoutManager(holder.recyclerView.context)
        viewAdapter = AdaptadorSeries(series)
        holder.anadirSerie.setOnClickListener {
            val dia=myDataset[position].dia
            val idEjercicio=myDataset[position].id
            val numeroSeries:Int=seriesDAO.numeroSeries(dia,idEjercicio)
            seriesDAO.insertarSerie(Serie(numeroSeries,0,0,0,dia,idEjercicio))
            notifyDataSetChanged()
        }
        holder.recyclerView.apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

            val swipeHandler = object : DeslizarParaBorrar(this.context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapterR = adapter as AdaptadorSeries
                    adapterR.removeAt(viewHolder.adapterPosition)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ejercicio, parent, false) as View

        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return myDataset.size
    }

}