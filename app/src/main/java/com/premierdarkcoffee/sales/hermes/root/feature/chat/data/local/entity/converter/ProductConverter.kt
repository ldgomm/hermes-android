package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity

class ProductConverter {

    @TypeConverter
    fun fromProductEntity(productEntity: ProductEntity?): String? {
        return productEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toProductEntity(json: String?): ProductEntity? {
        return json?.let { Gson().fromJson(it, ProductEntity::class.java) }
    }
}