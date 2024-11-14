package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.CategoryEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.CategoryDto

data class Category(val mi: String, val ni: String, val xi: String) {

    fun toCategoryDto(): CategoryDto {
        return CategoryDto(mi = mi, ni = ni, xi = xi)
    }

    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(mi = mi, ni = ni, xi = xi)
    }
}
