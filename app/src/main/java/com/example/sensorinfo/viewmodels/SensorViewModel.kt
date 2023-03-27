package com.example.sensorinfo.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sensorinfo.repositories.SensorRepository

class SensorViewModel: ViewModel() {
    private val repository = SensorRepository.get()
    val sensorlist = repository.sensorList
    val listAsMap = repository.listAsMaps()
}