package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.CategoryEntity

class CategoryConverter {
    @TypeConverter
    fun fromCategoryEntity(categoryEntity: CategoryEntity?): String? {
        return categoryEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCategoryEntity(json: String?): CategoryEntity? {
        return json?.let { Gson().fromJson(it, CategoryEntity::class.java) }
    }
}