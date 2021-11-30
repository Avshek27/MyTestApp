package com.test.network.service

import com.test.storage.LocationSearch
import com.test.model.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("location/search/?")
    fun getLocByLatLong(@Query("lattlong") type: String): Call<List<LocationSearch>>

    @GET("location/search/?")
    fun getLocByName(@Query("query") type: String): Call<List<LocationSearch>>

    // WeatherReport

    @GET("location/{woeid}")
    fun getWeatherReport(@Path("woeid") type: Int): Call<Model.WeatherReport>
}