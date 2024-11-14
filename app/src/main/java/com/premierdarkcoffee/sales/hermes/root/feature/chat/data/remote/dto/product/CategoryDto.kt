package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(val mi: String, val ni: String, val xi: String) {

    fun toCategory(): Category {
        return Category(mi = mi, ni = ni, xi = xi)
    }
}
