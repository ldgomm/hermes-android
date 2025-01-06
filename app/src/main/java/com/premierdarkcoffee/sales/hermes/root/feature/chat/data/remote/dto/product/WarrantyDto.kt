package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Warranty
import kotlinx.serialization.Serializable

@Serializable
data class WarrantyDto(val hasWarranty: Boolean, val details: List<String>, val months: Int) {

    fun toWarranty(): Warranty {
        return Warranty(hasWarranty = hasWarranty, details = details, months = months)
    }
}
