package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity

class StoreInformationConverter {

    @TypeConverter
    fun fromStoreInformationEntity(storeEntity: StoreEntity?): String? {
        return storeEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStoreInformationEntity(json: String?): StoreEntity? {
        return json?.let { Gson().fromJson(it, StoreEntity::class.java) }
    }
}
