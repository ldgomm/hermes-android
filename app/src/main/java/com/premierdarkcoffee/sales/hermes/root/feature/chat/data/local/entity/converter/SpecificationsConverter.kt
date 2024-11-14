package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.SpecificationsEntity

class SpecificationsConverter {

    @TypeConverter
    fun fromSpecificationsEntity(specificationsEntity: SpecificationsEntity?): String? {
        return specificationsEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toSpecificationsEntity(json: String?): SpecificationsEntity? {
        return json?.let { Gson().fromJson(it, SpecificationsEntity::class.java) }
    }
}