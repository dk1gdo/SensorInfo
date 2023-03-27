package com.example.sensorinfo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.sensorinfo.adapters.SensorRVAdapter
import com.example.sensorinfo.viewmodels.SensorViewModel

class MainActivity : AppCompatActivity(), SensorRVAdapter.ItemClickListener {
    lateinit var rvSensors: RecyclerView
    private val viewModel: SensorViewModel by lazy {
        ViewModelProvider(this)[SensorViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = SensorRVAdapter(this, viewModel.sensorlist)

        val rvListener = object : SensorRVAdapter.ItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                Toast.makeText(this@MainActivity, "position: $position", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setClickListener(rvListener)

        rvSensors = findViewById(R.id.rvSensors)
        rvSensors.layoutManager = LinearLayoutManager(this)
        rvSensors.adapter = adapter



        val spnColour = findViewById<Spinner>(R.id.spnColour)
        val spnAdapter = ArrayAdapter.createFromResource(this,
            R.array.coloursArray,
            android.R.layout.simple_list_item_1)
        spnColour.adapter = spnAdapter

        val spnColourListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> rvSensors.setBackgroundColor(Color.BLUE)
                    1 -> rvSensors.setBackgroundColor(Color.YELLOW)
                    2 -> rvSensors.setBackgroundColor(Color.GREEN)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        spnColour.onItemSelectedListener = spnColourListener
    }

    override fun onItemClick(view: View?, position: Int) {
        Toast.makeText(this, "position: $position", Toast.LENGTH_SHORT).show()
    }
}

