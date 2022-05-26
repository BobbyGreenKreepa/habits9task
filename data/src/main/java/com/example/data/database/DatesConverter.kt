package com.example.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatesConverter {

    @TypeConverter
    fun fromDates(doneDates: MutableList<Int>): String = Gson().toJson(doneDates)


    @TypeConverter
    fun toDates(datesString: String): MutableList<Int> {
        val listType = object : TypeToken<MutableList<Int>>() {}.type
        return Gson().fromJson(datesString, listType)
    }
}