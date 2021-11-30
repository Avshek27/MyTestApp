package com.test.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_loc")
data class LocationSearch(
    val title: String,
    val location_type: String,
    val woeid: Int,
    val latt_long: String,
    val distance: Double,
    val timeStamp: String?,
    val activeDate: String?,
    val active: Int = 0,
    @PrimaryKey(autoGenerate = false) val id: Int? = null
)