package com.example.tfg_fatigapr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.clasesDatos.Ejercicio

public class AdaptadorEjercicios(private val myDataset: List<Ejercicio>) :RecyclerView.Adapter<AdaptadorEjercicios.MyViewHolder>(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    class MyViewHolder: RecyclerView.ViewHolder
    {
        var textView:TextView
        var recyclerView:RecyclerView

        constructor(view: View) : super(view) {
            textView=view.findViewById(R.id.tv_ejercicio)
            recyclerView=view.findViewById(R.id.recycler_series)

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text=myDataset[position].nombre
        viewManager = LinearLayoutManager(holder.recyclerView.context)
        viewAdapter = AdaptadorSeries(myDataset[position].series)
        holder.recyclerView.apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
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