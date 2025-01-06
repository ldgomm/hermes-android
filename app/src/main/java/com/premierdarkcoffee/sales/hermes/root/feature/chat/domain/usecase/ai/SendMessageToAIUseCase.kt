package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.ai

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.request.ClientProductRequest
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.response.ClientProductResponse
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.ProductServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendMessageToAIUseCase @Inject constructor(private val productServiceable: ProductServiceable) {

    operator fun invoke(
        url: String,
        request: ClientProductRequest
    ): Flow<Result<ClientProductResponse>> {
        return productServiceable.searchProducts(url = url, request = request)
    }
}
