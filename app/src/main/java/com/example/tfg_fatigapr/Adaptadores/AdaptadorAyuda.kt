package com.example.tfg_fatigapr.Adaptadores

import android.animation.ObjectAnimator
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.ViewModels.ViewModelSeries
import com.google.firebase.firestore.DocumentSnapshot


/**
 * Clase para controlar el RecyclerView de los ejercicios
 *
 * @property ViewModelSeries ViewModel de las Series para controlar el RecyclerView anidado
 * @author Yeray Sardon Ibañez
 *
 */
class AdaptadorAyuda(myDataSet:List<DocumentSnapshot>, private val application: Application):
    RecyclerView.Adapter<AdaptadorAyuda.MyViewHolder>() {
    private val myDataSet=myDataSet

    /**
     * Clase que alberga las views del interior del RecyclerView
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var tituloAyuda:TextView = view.findViewById(R.id.titulo_ayuda)
        var descripcionAyuda:TextView = view.findViewById(R.id.descripcion_ayuda)
        var bt_desplegable:Button = view.findViewById(R.id.bt_expandirDescripcion)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_ayuda, parent, false) as View
        return MyViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val elementoAyuda=myDataSet[position].data
        holder.tituloAyuda.text= elementoAyuda?.get("titulo")!!.toString()
        holder.descripcionAyuda.text= elementoAyuda?.get("Descripcion")!!.toString()
        holder.bt_desplegable.setOnClickListener {
            if (holder.descripcionAyuda.maxLines == 1) {
                holder.bt_desplegable.background=
                    ContextCompat.getDrawable(
                        application,
                        R.drawable.ic_keyboard_arrow_down_black_24dp
                    )
                val animation = ObjectAnimator.ofInt(holder.descripcionAyuda, "maxLines", 10)
                animation.duration=100
                animation.start()
            } else {
                holder.bt_desplegable.background=
                    ContextCompat.getDrawable(
                        application,
                        R.drawable.ic_keyboard_arrow_up_black_24dp
                    )
                //This will expand the textview if it is of 2 lines
                val animation = ObjectAnimator.ofInt(holder.descripcionAyuda, "maxLines", 1)
                animation.duration=100
                animation.start()

            }
        }
    }
}