package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Warranty

data class WarrantyEntity(val hasWarranty: Boolean, val details: List<String>, val months: Int) {

    fun toWarranty(): Warranty {
        return Warranty(hasWarranty = hasWarranty, details = details, months = months)
    }
}
