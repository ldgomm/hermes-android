package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreStatusEntity

class StoreStatusConverter {

    @TypeConverter
    fun fromStoreStatusEntity(statusEntity: StoreStatusEntity?): String? {
        return statusEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStoreStatusEntity(json: String?): StoreStatusEntity? {
        return json?.let { Gson().fromJson(it, StoreStatusEntity::class.java) }
    }
}