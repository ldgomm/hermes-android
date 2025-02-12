package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.CodesEntity

class CodesConverter {
    @TypeConverter
    fun fromCodesEntity(codesEntity: CodesEntity?): String? {
        return codesEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCodesEntity(json: String?): CodesEntity? {
        return json?.let { Gson().fromJson(it, CodesEntity::class.java) }
    }
}
