package com.test.utility

import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utility {

    companion object {
        fun getTimeStamp(): String {
            return Timestamp(System.currentTimeMillis()).toString()
        }

        fun dateToTimeStamp(date: String): Long {
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
            val mDate = formatter.parse(date) as Date
            return mDate.time

        }

        fun add5Days(date: String, days: Int): Long {
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
            val mDate = formatter.parse(date) as Date
            val cal = Calendar.getInstance()
            cal.timeInMillis = mDate.time

            // add 5 days
            cal.add(Calendar.HOUR, 24*days)
            return cal.time.time
        }

        const val DAYS: Int = 5
    }
}