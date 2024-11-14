package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringConverter {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.let { Gson().fromJson(it, object : TypeToken<List<String>>() {}.type) }
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.let { Gson().toJson(it) }
    }
}