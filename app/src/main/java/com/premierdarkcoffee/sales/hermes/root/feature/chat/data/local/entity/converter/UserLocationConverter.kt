package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

//
//  LocationConverter.kt
//  Hermes
//
//  Created by José Ruiz on 7/8/24.
//

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.UserLocationEntity

class UserLocationConverter {
    @TypeConverter
    fun fromUserLocationEntity(userLocationEntity: UserLocationEntity?): String? {
        return userLocationEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toUserUserLocationEntity(json: String?): UserLocationEntity? {
        return json?.let { Gson().fromJson(it, UserLocationEntity::class.java) }
    }
}