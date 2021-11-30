package com.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.storage.LocationSearch
import com.test.repository.DBRepository

class LSViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = DBRepository(app)
    private val allLocations = repository.getAllSearchLocation()

    /**
     * to insert new record in table
     */
    fun insert(data: LocationSearch) {
        repository.insert(data)
    }

    /**
     * to update existing record
     */
    fun update(id: Int, date: String, value: Int) {
        repository.update(id, date, value)
    }

    fun getActiveLocation(): List<LocationSearch> {
        return repository.getActiveLocation()
    }

    /**
     * to a row from table
     */
    fun delete(table: LocationSearch) {
        repository.delete(table)
    }

    /**
     * to delete all data from table
     */
    fun deleteAll() {
         repository.deleteAllSearch()
    }

    /**
     * to get all data from table
     */
    fun getAllLocation(): LiveData<List<LocationSearch>> {
        return allLocations
    }

    fun isLocationExist(id: Int): Boolean {
        return repository.isLocationExist(id)
    }

}