package com.example.tfg_fatigapr.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.Utilidades.DeslizarParaBorrar
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.ViewModels.ViewModelSeries
import com.example.tfg_fatigapr.clasesDatos.Ejercicio


class AdaptadorEjercicios (viewModelSerie: ViewModelSeries):
    RecyclerView.Adapter<AdaptadorEjercicios.MyViewHolder>() {
    private var myDataset: List<Ejercicio> = emptyList()
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var viewModeSerie: ViewModelSeries =viewModelSerie
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var textView:TextView = view.findViewById(R.id.tv_ejercicio)
        var recyclerView:RecyclerView = view.findViewById(R.id.recycler_series)
        var anadirSerie:TextView=view.findViewById(R.id.tv_anadirSerie)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ejercicioActual=myDataset[position]
        val context=holder.textView.context
        viewModeSerie.ponderDia(ejercicioActual.dia)
        holder.textView.text=context.getString(
            R.string.nombre_ejercicio_modificaciones,
            ejercicioActual.nombre,ejercicioActual.modificaciones)
        viewModeSerie.seleccionarSeriesdeEjercicio(ejercicioActual.dia,ejercicioActual.id)
        viewManager = LinearLayoutManager(context)
        viewAdapter =
            AdaptadorSeries(viewModeSerie)
        holder.anadirSerie.setOnClickListener {
            viewModeSerie.insertarSerie(ejercicioActual.id)
            notifyDataSetChanged()
        }
        holder.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            val swipeHandler = object : DeslizarParaBorrar(context) {
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
        return MyViewHolder(
            view
        )
    }
    override fun getItemCount(): Int {
        return myDataset.size
    }

    fun setItems(ejercicios:List<Ejercicio>){
        myDataset=ejercicios
        notifyDataSetChanged()
    }

}