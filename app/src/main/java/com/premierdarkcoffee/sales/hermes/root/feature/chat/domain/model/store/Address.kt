package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.AddressEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.AddressDto

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,
    val location: GeoPoint
) {

    fun toAddressDto(): AddressDto {
        return AddressDto(
            street = street, city = city, state = state, zipCode = zipCode, country = country, location = location.toGeoPointDto()
        )
    }

    fun toAddressEntity(): AddressEntity {
        return AddressEntity(
            street = street,
            city = city,
            state = state,
            zipCode = zipCode,
            country = country,
            location = location.toGeoPointEntity()
        )
    }
}

