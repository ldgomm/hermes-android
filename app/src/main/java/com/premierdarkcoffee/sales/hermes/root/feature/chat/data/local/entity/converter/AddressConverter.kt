package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.AddressEntity

class AddressConverter {
    @TypeConverter
    fun fromAddressEntity(addressEntity: AddressEntity?): String? {
        return addressEntity?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toAddressEntity(json: String?): AddressEntity? {
        return json?.let { Gson().fromJson(it, AddressEntity::class.java) }
    }
}
