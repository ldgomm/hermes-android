package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat.Cart

@Entity(tableName = "cart")
data class CartEntity(@PrimaryKey(autoGenerate = false) val id: String, val product: ProductEntity, val date: Long) {

    fun toCart(): Cart {
        return Cart(id = id, product = product.toProduct(), date = date)
    }
}