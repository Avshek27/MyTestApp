package com.test.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbDao {

    @Insert
    fun insert(searchTable: LocationSearch)

    @Query("update search_loc set activeDate = :date, active = :value where woeid = :id")
    fun update(id: Int, date: String, value: Int)

    @Query("select * from search_loc where active = 1")
    fun getActiveLocation(): List<LocationSearch>

    @Delete
    fun delete(note: LocationSearch)

    @Query("delete from search_loc")
    fun deleteAll()

    @Query("select * from search_loc")
    fun getAllLocation(): LiveData<List<LocationSearch>>

    @Query("SELECT EXISTS(SELECT * FROM search_loc WHERE woeid = :id)")
    fun isLocationExist(id: Int): Boolean
}