package com.premierdarkcoffee.sales.hermes.root.feature.authentication.data.remote.dto

//
//  ClientDto.kt
//  Hermes
//
//  Created by José Ruiz on 13/12/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.ImageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.GeoPointDto
import kotlinx.serialization.Serializable

@Serializable
data class ClientDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val image: ImageDto,
    val location: GeoPointDto,
    val createdAt: Long
)