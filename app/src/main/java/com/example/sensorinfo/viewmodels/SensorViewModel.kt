package com.example.sensorinfo.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sensorinfo.repositories.SensorRepository

class SensorViewModel: ViewModel() {
    private val repository = SensorRepository.get()
    val sensorlist = repository.sensorList
    val listAsMap = repository.listAsMaps()
    var selected = 0
        set(value) {
            if (value in 0..2) field = value
        }
    fun setSelected(str: String?) {
        if (!str.isNullOrBlank()) selected = try {
            str.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }
}