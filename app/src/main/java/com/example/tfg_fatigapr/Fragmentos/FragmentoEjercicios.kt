package com.example.tfg_fatigapr.Fragmentos


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tfg_fatigapr.databinding.ContentMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.*
import com.example.tfg_fatigapr.Adaptadores.AdaptadorEjercicios
import com.example.tfg_fatigapr.Utilidades.AnadirEjercicio
import com.example.tfg_fatigapr.ViewModels.ViewModelEjercicios
import com.example.tfg_fatigapr.ViewModels.ViewModelEjerciciosFactory
import com.example.tfg_fatigapr.ViewModels.ViewModelSeries
import com.example.tfg_fatigapr.ViewModels.ViewModelSeriesFactory
import java.util.*


class FragmentoEjercicios : Fragment() {
    private lateinit var viewAdapter: AdaptadorEjercicios
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: ViewModelEjercicios
    private lateinit var viewModelSerie: ViewModelSeries
    private lateinit var binding:ContentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,
            R.layout.content_main,container, false)
        viewModel=ViewModelProvider(this,
            ViewModelEjerciciosFactory(
                context!!
            )
        ).get(ViewModelEjercicios::class.java)
        viewModelSerie=ViewModelProvider(this,
            ViewModelSeriesFactory(context!!)
        ).get(ViewModelSeries::class.java)
        binding.btDiaPosterior.setOnClickListener{modificarDia(1)}
        binding.btDiaAnterior.setOnClickListener{modificarDia(-1)}
        binding.btAnadirEjercicio.setOnClickListener { anadirEjericio()}
        binding.dia.setOnClickListener{diaActual()}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewAdapter =
            AdaptadorEjercicios(
                viewModelSerie
            )
        viewManager = LinearLayoutManager(context)
        binding.recyclerEjercicios.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        actualizardia()
        cargarDia()
    }

    private fun modificarDia(operacion:Int){
        val c=viewModel.modificarDia(operacion)
        actualizardia()
        actualizarSelector(
            c.get(Calendar.YEAR),
            (c.get(Calendar.MONTH) + 1),
            c.get(Calendar.DATE)
        )

        cargarDia()
    }


    private fun anadirEjericio(){
        val intentAnadirEjercicio = Intent(context, AnadirEjercicio::class.java)
        intentAnadirEjercicio.putExtra(
            "dia",viewModel.dia)
        startActivityForResult(intentAnadirEjercicio, 1)
    }

    private fun diaActual(){
        if (binding.selectorEjercicio.visibility == View.VISIBLE) {
            viewModel.actualizarDiaDP(getString(
                R.string.formatodiamesao,
                binding.selectorEjercicio.dayOfMonth.toString(),
                binding.selectorEjercicio.month.toString(),
                binding.selectorEjercicio.year.toString()
            ))
            actualizardia()
            cargarDia()
            binding.selectorEjercicio.visibility = View.GONE

        } else
            binding.selectorEjercicio.visibility = View.VISIBLE
    }

    private fun actualizardia() {
        binding.dia.text = viewModel.dia
    }

    private fun cargarDia(){
        viewAdapter.setItems(viewModel.seleccionarEjercicios(binding.dia.text.toString()))
    }

    private fun actualizarSelector(year:Int, mes:Int, dia:Int){
        binding.selectorEjercicio.updateDate(year,mes,dia)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == 1  && resultCode  == 0) {
                cargarDia()

            }
        } catch (ex:Exception) {

        }

    }
}
