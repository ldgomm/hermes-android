package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.WarrantyEntity

class WarrantyConverter {

    @TypeConverter
    fun fromWarrantyEntity(warrantyEntity: WarrantyEntity?): String? {
        return warrantyEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toWarrantyEntity(json: String?): WarrantyEntity? {
        return json?.let { Gson().fromJson(it, WarrantyEntity::class.java) }
    }
}
