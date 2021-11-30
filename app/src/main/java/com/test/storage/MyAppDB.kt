package com.test.storage

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.test.utility.subscribeOnBackground

@Database(entities = [LocationSearch::class], version = 1)
abstract class MyAppDB : RoomDatabase() {

    abstract fun dbDao(): DbDao

    companion object {
        private var instance: MyAppDB? = null

        fun getInstance(ctx: Context): MyAppDB {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, MyAppDB::class.java,
                    "MyApp_database"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            Log.d("DB", instance.toString())
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                populateDatabase(instance!!)
            }
        }

        fun populateDatabase(db: MyAppDB) {
            val noteDao = db.dbDao()
            Log.d("DB", "populateDatabase")
            subscribeOnBackground {
                // noteDao.insert(LocationSearch("Mumbai", "City", 12586539, "19.076191,72.875877", "1637561908"))
                // noteDao.insert(LocationSearch("Delhi", "City", 12586539, "19.076191,72.875877", "1637561908"))
                // noteDao.insert(LocationSearch("Kolkata", "City", 12586539, "19.076191,72.875877", "1637561908"))
            }
        }
    }
}