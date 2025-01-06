package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(val group: String, val domain: String, val subclass: String) {

    fun toCategory(): Category {
        return Category(group = group, domain = domain, subclass = subclass)
    }
}
