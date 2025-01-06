package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.cart

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.CartRepositoriable
import javax.inject.Inject

class DeleteCartProductUseCase @Inject constructor(private val cartRepositoriable: CartRepositoriable) {

    suspend operator fun invoke(id: String) {
        cartRepositoriable.deleteProduct(id = id)
    }
}
