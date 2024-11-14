package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Category
import androidx.room.Entity

data class CategoryEntity(val mi: String, val ni: String, val xi: String) {

    fun toCategory(): Category {
        return Category(mi = mi, ni = ni, xi = xi)
    }
}