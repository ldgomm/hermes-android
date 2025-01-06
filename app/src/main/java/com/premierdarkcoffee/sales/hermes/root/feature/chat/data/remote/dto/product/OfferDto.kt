package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Offer
import kotlinx.serialization.Serializable

@Serializable
data class OfferDto(val isActive: Boolean, val discount: Int) {

    fun toOffer(): Offer {
        return Offer(isActive = isActive, discount = discount)
    }
}
