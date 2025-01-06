package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.PriceEntity

class PriceConverter {

    @TypeConverter
    fun fromPriceEntity(priceEntity: PriceEntity?): String? {
        return priceEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toPriceEntity(json: String?): PriceEntity? {
        return json?.let { Gson().fromJson(it, PriceEntity::class.java) }
    }
}
