package com.example.tfg_fatigapr


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
                val tipoUsurio = genericType<List<Usuario>>()
                usuarios = Gson().fromJson<List<Usuario>>(jsonPrueba, tipoUsurio)
                viewManager = LinearLayoutManager(context)
                viewAdapter = AdaptadorEjercicios(usuarios[0].dia)
                var dia=view.findViewById<TextView>(R.id.dia)
                dia.text=usuarios[0].dia[0].id
                recyclerView = view.findViewById<RecyclerView>(R.id.recycler_ejercicios).apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = viewManager

                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                }
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
    fun retjson():String{
        return "[{id:5dc68d22f5c6b0cd58eb6ad0,nombre:Leonor,peso:64,dia:[{id:2014-11-30,"+
                "nombre:Georgette,modificaciones: 0,ejercicios: [{id: 0,peso: 14,RPE: 8,reps: 1"+
                "},{id: 1,peso: 96,RPE: 8,reps: 6},{id: 2,peso: 13,RPE: 1,reps: 2}]},{id: 2015-01-06," +
                "nombre: Bowers,modificaciones: 1,ejercicios: [{id: 0,peso: 162,RPE: 5,reps: 3},{id: 1,peso: 90,"+
                "RPE: 10,reps: 5},{id: 2,peso: 160,RPE: 8,reps: 5}]},{id: 2015-03-11,nombre: Hancock,modificaciones: 2,"+
                "ejercicios: [{id: 0,peso: 199,RPE: 4,reps: 8},{id: 1,peso: 37,RPE: 1,reps: 1},{id: 2,peso: 179,RPE: 1,reps: 1}]}]}]"
    }
}
