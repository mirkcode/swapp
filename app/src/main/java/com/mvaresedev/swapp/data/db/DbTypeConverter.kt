package com.mvaresedev.swapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DbTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromListToJson(list: List<Int>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromJsonToList(json: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, listType)
    }
}