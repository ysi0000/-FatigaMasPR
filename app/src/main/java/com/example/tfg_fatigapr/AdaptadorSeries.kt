package com.example.tfg_fatigapr

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class AdaptadorSeries(private val myDataset: List<Serie>) :RecyclerView.Adapter<AdaptadorSeries.MyViewHolder>(){

    class MyViewHolder: RecyclerView.ViewHolder
    {
        var peso:EditText
        var repeticiones:EditText
        var RPE:EditText
        constructor(view: View) : super(view) {
            peso=view.findViewById(R.id.edittext_peso)
            repeticiones=view.findViewById(R.id.edittext_repeticiones)
            RPE=view.findViewById(R.id.edittext_RPE)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.peso.setText(Integer.toString(myDataset[position].peso))
        holder.repeticiones.setText(Integer.toString(myDataset[position].reps))
        holder.RPE.setText(Integer.toString(myDataset[position].RPE))
        Log.d("Posicion",position.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.series, parent, false) as View
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return myDataset.size
    }

}