package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.cart

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.cart.CartEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.CartRepositoriable
import javax.inject.Inject

class ReadProductsFromCartUseCase @Inject constructor(private val cartRepositoriable: CartRepositoriable) {

    suspend operator fun invoke(): List<CartEntity> {
        return cartRepositoriable.readProductsInformation()
    }
}
