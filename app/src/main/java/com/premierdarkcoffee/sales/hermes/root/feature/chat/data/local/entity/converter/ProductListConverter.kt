package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity

class ProductListConverter {

    @TypeConverter
    fun fromProductList(list: List<ProductEntity>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toProductList(value: String?): List<ProductEntity>? {
        return value?.let { Gson().fromJson(it, object : TypeToken<List<ProductEntity>>() {}.type) }
    }
}
