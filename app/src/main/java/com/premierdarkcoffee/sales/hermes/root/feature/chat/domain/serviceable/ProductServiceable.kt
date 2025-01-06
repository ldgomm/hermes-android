package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.request.ClientProductRequest
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.response.ClientProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductServiceable {

    fun searchProducts(url: String, request: ClientProductRequest): Flow<Result<ClientProductResponse>>
}
