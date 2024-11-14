package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.cart.CartEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity

interface CartRepositoriable {

    suspend fun createProductInformation(product: ProductEntity)

    suspend fun readProductsInformation(): List<CartEntity>

    suspend fun deleteProduct(id: String)
}
