package com.example.tfg_fatigapr.Fragmentos

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.color
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.Adaptadores.AdaptadorAyuda
import com.example.tfg_fatigapr.Adaptadores.AdaptadorEjercicios
import com.example.tfg_fatigapr.R
import com.example.tfg_fatigapr.ViewModels.ViewModelEjercicios
import com.example.tfg_fatigapr.ViewModels.ViewModelEjerciciosFactory
import com.example.tfg_fatigapr.ViewModels.ViewModelSeries
import com.example.tfg_fatigapr.ViewModels.ViewModelSeriesFactory
import com.example.tfg_fatigapr.databinding.AyudaBinding
import com.example.tfg_fatigapr.databinding.ContentMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.remote.RemoteStore
import kotlinx.coroutines.*

class FragmentoAyuda : Fragment(){
    private lateinit var viewAdapter: AdaptadorAyuda
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: AyudaBinding
    lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,
            R.layout.ayuda,container, false)
        db=FirebaseFirestore.getInstance()


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db.collection("Ayuda")
            .get()
            .addOnSuccessListener { result ->
                viewAdapter =
                    AdaptadorAyuda(result.documents,requireActivity().application)
                viewManager = LinearLayoutManager(context)
                binding.recyclerAyuda.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter

                }

                /*for (document in result) {
                    Log.d("Prueba", "${document.id} => ${document.data}")
                }*/
            }
            .addOnFailureListener { exception ->
                Log.w("Prueba", "Error getting documents.", exception)
            }


    }

}