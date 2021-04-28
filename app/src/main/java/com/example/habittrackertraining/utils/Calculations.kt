package com.example.habittrackertraining.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Calculations {

    fun calculaterTimeBetweenDates(startDate: String): String {

        val endDate = timeStampToString(System.currentTimeMillis())

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val date1 = sdf.parse(startDate)
        val date2 = sdf.parse(endDate)

        var isNegative = false

        var difference = date2.time - date1.time

        if (difference < 0) {
            difference = -(difference)
            isNegative = true
        }

        val minutes = difference/60/1000
        val hours = difference/60/1000/60
        val days = (difference/60/1000/60)/24
        val months = (difference/60/1000/60)/24/(365/12)
        val years = difference/60/1000/60/24/365

        if (isNegative) {
            return when {
                minutes < 240 -> "$minutes 分鐘內開始"
                hours < 48 -> "$hours 小時內開始"
                days < 61 -> "$days 天內開始"
                months < 24 -> "$months 個月內開始"
                else -> "$years 年內開始"
            }
        }

        return when {
            minutes < 240 -> "$minutes 分鐘前"
            hours < 48 -> "$hours 小時前"
            days < 61 -> "$days 天前"
            months < 24 -> "$months 個月前"
            else -> "$years 年前"
        }

    }

    private fun timeStampToString(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val date = sdf.format((Date(stamp.time)))

        return date.toString()
    }

    fun cleanDate(_day: Int, _month:Int, _year: Int): String {
        var day = _day.toString()
        var month = _month.toString()

        if (_day < 10) {
            day = "0$_day"
        }

        if (_month < 9) {
            month = "0${_month+1}"
        }
        return "$_year/$month/$day"
    }

    fun cleanTime(_hour: Int, _minute: Int): String {
        var hour = _hour.toString()
        var minute = _minute.toString()

        if (_hour < 10) {
            hour = "0$_hour"
        }

        if (_minute < 10) {
            minute = "0$_minute"
        }

        return "$hour:$minute"
    }

}