package com.example.tfg_fatigapr

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import com.example.tfg_fatigapr.clasesDatos.Serie
import kotlinx.android.synthetic.main.nav_header_main.view.*

public class AdaptadorSeries : RecyclerView.Adapter<AdaptadorSeries.MyViewHolder> {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val myDataset: List<Serie>
    private val diaActual:String
    private val ejericicio:String
    constructor(myDataset: List<Serie>, diaActual: String,ejercicio: String) : super() {
        this.myDataset = myDataset
        this.diaActual=diaActual
        this.ejericicio=ejercicio
    }
    class MyViewHolder: RecyclerView.ViewHolder
    {
        var peso:EditText
        var repeticiones:EditText
        var RPE:EditText
        var id:TextView
        constructor(view: View) : super(view) {
            id=view.findViewById(R.id.id_serie)
            peso=view.findViewById(R.id.edittext_peso)
            repeticiones=view.findViewById(R.id.edittext_repeticiones)
            RPE=view.findViewById(R.id.edittext_RPE)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text=(position+1).toString()
        holder.peso.setText(Integer.toString(myDataset[position].peso))
        holder.peso.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString()!="") {
                    val contextview = holder.peso.context
                    val db = RoomDataBase.getInstance(contextview)
                    val sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(contextview)
                    val st = contextview.getString(R.string.key_editpref_nombre)
                    val str = sharedPreferences.getString(st, "")!!
                    val usuario = db!!.usuariosDAO().seleccionarusuario(str)
                    usuario.cambiarPeso(
                        diaActual,
                        ejericicio,
                        holder.id.text.toString().toInt() - 1,
                        p0.toString().toInt()
                    )
                    db!!.usuariosDAO().actualizarDias(usuario.nombre, usuario.dia)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        holder.repeticiones.setText(Integer.toString(myDataset[position].reps))
        holder.repeticiones.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString()!="") {
                    val contextview = holder.peso.context
                    val db = RoomDataBase.getInstance(contextview)
                    val sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(contextview)
                    val st = contextview.getString(R.string.key_editpref_nombre)
                    val str = sharedPreferences.getString(st, "")!!
                    val usuario = db!!.usuariosDAO().seleccionarusuario(str)

                    usuario.cambiarReps(
                        diaActual,
                        ejericicio,
                        holder.id.text.toString().toInt() - 1,
                        p0.toString().toInt()
                    )
                    db!!.usuariosDAO().actualizarDias(usuario.nombre, usuario.dia)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        holder.RPE.setText(Integer.toString(myDataset[position].RPE))
        holder.RPE.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString()!="") {
                    val contextview = holder.peso.context
                    val db = RoomDataBase.getInstance(contextview)
                    val sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(contextview)
                    val st = contextview.getString(R.string.key_editpref_nombre)
                    val str = sharedPreferences.getString(st, "")!!
                    val usuario = db!!.usuariosDAO().seleccionarusuario(str)
                    usuario.cambiarRPE(
                        diaActual,
                        ejericicio,
                        holder.id.text.toString().toInt() - 1,
                        p0.toString().toInt()
                    )
                    db!!.usuariosDAO().actualizarDias(usuario.nombre, usuario.dia)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
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