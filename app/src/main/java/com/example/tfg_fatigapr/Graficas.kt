package com.example.tfg_fatigapr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class Graficas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graficas)
        val lineChartView = findViewById<LineChart>(R.id.linechart)
        val lineDataSet= LineDataSet(dataValues(),"Data Set")
        val datasets=ArrayList<ILineDataSet>()
        datasets.add(lineDataSet)
        val data=LineData(datasets)
        lineChartView.data=data
        lineChartView.invalidate()

    }

    private fun dataValues(): ArrayList<Entry> {
        val dataset=ArrayList<Entry>()
        dataset.add(Entry(0f,20f))
        dataset.add(Entry(1f,30f))
        dataset.add(Entry(2f,40f))
        dataset.add(Entry(3f,50f))
        return dataset
    }

}
