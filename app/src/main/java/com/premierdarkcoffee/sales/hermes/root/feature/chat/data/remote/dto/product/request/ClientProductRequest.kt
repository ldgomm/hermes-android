package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.request

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.GeoPointDto
import kotlinx.serialization.Serializable

@Serializable
data class ClientProductRequest(val key: String? = null,
                                val query: String,
                                val clientId: String,
                                val location: GeoPointDto,
                                val distance: Int)
