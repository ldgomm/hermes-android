package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.CategoryEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.CategoryDto

data class Category(val group: String, val domain: String, val subclass: String) {

    fun toCategoryDto(): CategoryDto {
        return CategoryDto(group = group, domain = domain, subclass = subclass)
    }

    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(group = group, domain = domain, subclass = subclass)
    }
}
