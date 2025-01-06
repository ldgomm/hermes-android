package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.cart.CartEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product

data class Cart(val id: String, val product: Product, val date: Long) {

    fun toCartEntity(): CartEntity {
        return CartEntity(id = id, product = product.toProductEntity(), date = date)
    }
}
