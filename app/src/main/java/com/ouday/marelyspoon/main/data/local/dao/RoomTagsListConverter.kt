package com.ouday.marelyspoon.main.data.local.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class RoomTagsListConverter {


    @TypeConverter
    fun stringifyList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun parseString(str: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(str, type)
    }

}
