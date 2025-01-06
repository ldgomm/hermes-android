package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.response

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.ProductDto
import kotlinx.serialization.Serializable

@Serializable
data class ClientProductResponse(val firstMessage: String,
                                 val products: List<ProductDto>? = null,
                                 val secondMessage: String? = null,
                                 val optionalProducts: List<ProductDto>? = null)