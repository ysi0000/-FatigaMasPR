package com.example.tfg_fatigapr


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
public class FragmentoGraficas : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_graficas, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lineChartView = view.findViewById<LineChart>(R.id.linechart)
        var lineDataSet = LineDataSet(dataValues(), "Data Set")
        var datasets = ArrayList<ILineDataSet>()
        datasets.add(lineDataSet)
        var data = LineData(datasets)
        lineChartView.data = data
        lineChartView.invalidate()
    }
    private fun dataValues(): ArrayList<Entry> {
        var dataset = ArrayList<Entry>()
        dataset.add(Entry(0f, 20f))
        dataset.add(Entry(1f, 30f))
        dataset.add(Entry(2f, 40f))
        dataset.add(Entry(3f, 50f))
        return dataset
    }

}

