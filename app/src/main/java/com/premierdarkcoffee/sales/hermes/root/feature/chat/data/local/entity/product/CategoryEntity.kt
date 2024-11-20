package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Category

data class CategoryEntity(val group: String, val domain: String, val subclass: String) {

    fun toCategory(): Category {
        return Category(group = group, domain = domain, subclass = subclass)
    }
}