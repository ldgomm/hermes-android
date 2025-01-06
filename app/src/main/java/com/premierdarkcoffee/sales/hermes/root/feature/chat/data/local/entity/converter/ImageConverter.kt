package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ImageEntity

class ImageConverter {

    @TypeConverter
    fun fromImagesEntity(imageEntity: ImageEntity?): String? {
        return imageEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toImagesEntity(json: String?): ImageEntity? {
        return json?.let { Gson().fromJson(it, ImageEntity::class.java) }
    }
}
