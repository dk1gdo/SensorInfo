package com.example.sensorinfo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorinfo.adapters.SensorRVAdapter
import com.example.sensorinfo.viewmodels.SensorViewModel

class MainActivity : AppCompatActivity(), SensorRVAdapter.ItemClickListener {
    lateinit var rvSensors: RecyclerView
    private lateinit var spnColour: Spinner
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
        spnColour = findViewById(R.id.spnColour)
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
                viewModel.selected = position
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        spnColour.onItemSelectedListener = spnColourListener
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuPref -> {
                val settingsActivity = Intent(this, SettingsActivity::class.java)
                startActivity(settingsActivity)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onItemClick(view: View?, position: Int) {
        Toast.makeText(this, "position: $position", Toast.LENGTH_SHORT).show()
    }
    private fun applyPreferences() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        viewModel.setSelected(pref.getString("selected", viewModel.selected.toString()))
        spnColour.setSelection(viewModel.selected)
    }
    override fun onStart() {
        super.onStart()
        applyPreferences()
    }
}
