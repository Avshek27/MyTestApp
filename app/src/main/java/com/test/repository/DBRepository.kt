package com.test.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.test.storage.DbDao
import com.test.storage.LocationSearch
import com.test.storage.MyAppDB
import com.test.utility.Utility
import com.test.utility.subscribeOnBackground

class DBRepository(application: Application) {

    private var dbDao: DbDao
    private var allSearchLocation: LiveData<List<LocationSearch>>

    private val database = MyAppDB.getInstance(application)

    init {
        dbDao = database.dbDao()
        allSearchLocation = dbDao.getAllLocation()
    }

    fun insert(data: LocationSearch) {
        subscribeOnBackground {
            dbDao.insert(data)
        }
    }

    fun update(id: Int, date: String, value: Int) {
        subscribeOnBackground {
            dbDao.update(id, date, value)
        }
    }

    fun getActiveLocation(): List<LocationSearch> {
        return dbDao.getActiveLocation()
    }

    fun delete(data: LocationSearch) {
        subscribeOnBackground {
            dbDao.delete(data)
        }
    }

    fun deleteAllSearch() {
        subscribeOnBackground {
            dbDao.deleteAll()
        }
    }

    fun getAllSearchLocation(): LiveData<List<LocationSearch>> {
        return allSearchLocation
    }

    fun isLocationExist(id: Int): Boolean {
        return dbDao.isLocationExist(id)
    }
}