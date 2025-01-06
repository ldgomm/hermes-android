package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Address
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(val street: String,
                      val city: String,
                      val state: String,
                      val zipCode: String,
                      val country: String,
                      val location: GeoPointDto) {

    fun toAddress(): Address {
        return Address(street, city, state, zipCode, country, location = location.toGeoPoint())
    }
}
