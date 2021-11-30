package com.test.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.test.storage.LocationSearch
import com.test.model.Model
import com.test.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainDataRepository {

    val locationByLatLong = MutableLiveData<List<LocationSearch>>()
    val locationByName = MutableLiveData<List<LocationSearch>>()
    val wheatherReport = MutableLiveData<Model.WeatherReport>()

    /**
     *
     */
    fun getLocByLatLong(latLong: String): MutableLiveData<List<LocationSearch>> {

        val call = RetrofitClient.API_INTERFACE.getLocByLatLong(latLong)
        call.enqueue(object : Callback<List<LocationSearch>> {
            override fun onFailure(call: Call<List<LocationSearch>>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.d("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<LocationSearch>>,
                response: Response<List<LocationSearch>>
            ) {
                // TODO("Not yet implemented")
                Log.d("DEBUG : ", response.body().toString())

                val data = response.body()

                locationByLatLong.value = data
            }
        })

        return locationByLatLong
    }

    /**
     *
     */
    fun getLocByName(name: String): MutableLiveData<List<LocationSearch>> {

        val call = RetrofitClient.API_INTERFACE.getLocByName(name)
        call.enqueue(object : Callback<List<LocationSearch>> {
            override fun onFailure(call: Call<List<LocationSearch>>, t: Throwable) {
                Log.d("DEBUG : ", t.message.toString())
            }
            override fun onResponse(
                call: Call<List<LocationSearch>>,
                response: Response<List<LocationSearch>>
            ) {
                Log.d("DEBUG : ", response.body().toString())
                val data = response.body()

                locationByName.value = data
            }
        })

        return locationByName
    }

    /**
     *
     */
    fun getWeatherReport(id: Int): MutableLiveData<Model.WeatherReport> {

        val call = RetrofitClient.API_INTERFACE.getWeatherReport(id)
        call.enqueue(object : Callback<Model.WeatherReport> {
            override fun onFailure(call: Call<Model.WeatherReport>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.d("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<Model.WeatherReport>,
                response: Response<Model.WeatherReport>
            ) {
                // TODO("Not yet implemented")
                Log.d("DEBUG : ", response.body().toString())

                val data = response.body()

                wheatherReport.value = data
            }
        })

        return wheatherReport
    }
}