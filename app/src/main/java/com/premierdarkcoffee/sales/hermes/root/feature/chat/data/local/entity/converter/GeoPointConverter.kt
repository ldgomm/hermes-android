package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

//
//  LocationConverter.kt
//  Hermes
//
//  Created by José Ruiz on 7/8/24.
//

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.GeoPointEntity

class GeoPointConverter {
    @TypeConverter
    fun fromGeoPointEntity(geoPointEntity: GeoPointEntity?): String? {
        return geoPointEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toGeoPointEntity(json: String?): GeoPointEntity? {
        return json?.let { Gson().fromJson(it, GeoPointEntity::class.java) }
    }
}