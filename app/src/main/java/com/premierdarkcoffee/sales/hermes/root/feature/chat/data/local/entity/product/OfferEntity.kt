package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Offer
import androidx.room.Entity

data class OfferEntity(val isActive: Boolean, val discount: Int) {

    fun toOffer(): Offer {
        return Offer(isActive = isActive, discount = discount)
    }
}
