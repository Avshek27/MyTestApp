package com.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.storage.LocationSearch
import com.test.model.Model
import com.test.repository.MainDataRepository

class MainDataViewModel : ViewModel() {

    private var locationByLatLong: MutableLiveData<List<LocationSearch>>? = null
    private var locationByName: MutableLiveData<List<LocationSearch>>? = null
    private var weatherReport: MutableLiveData<Model.WeatherReport>? = null

    fun getLocByLatLong(latLong: String): LiveData<List<LocationSearch>>? {
        locationByLatLong = MainDataRepository.getLocByLatLong(latLong)
        return locationByLatLong
    }

    fun getLocByName(name: String): LiveData<List<LocationSearch>>? {
        locationByName = MainDataRepository.getLocByName(name)
        return locationByName
    }

    fun getWeatherReport(id: Int): LiveData<Model.WeatherReport>? {
        weatherReport = MainDataRepository.getWeatherReport(id)
        return weatherReport
    }
}