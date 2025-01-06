package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.cart

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.CartRepositoriable
import javax.inject.Inject

class InsertProductToCartUseCase @Inject constructor(private val cartRepositoriable: CartRepositoriable) {

    suspend operator fun invoke(product: ProductEntity) {
        cartRepositoriable.createProductInformation(product)
    }
}
