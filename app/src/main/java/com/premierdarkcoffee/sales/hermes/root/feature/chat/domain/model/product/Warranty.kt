package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.WarrantyEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.WarrantyDto

data class Warranty(val hasWarranty: Boolean, val details: List<String>, val months: Int) {

    fun toWarrantyDto(): WarrantyDto {
        return WarrantyDto(hasWarranty = hasWarranty, details = details, months = months)
    }

    fun toWarrantyEntity(): WarrantyEntity {
        return WarrantyEntity(hasWarranty = hasWarranty, details = details, months = months)
    }
}
