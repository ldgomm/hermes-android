package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.OfferEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.OfferDto

data class Offer(val isActive: Boolean, val discount: Int) {

    fun toOfferDto(): OfferDto {
        return OfferDto(isActive = isActive, discount = discount)
    }

    fun toOfferEntity(): OfferEntity {
        return OfferEntity(isActive = isActive, discount = discount)
    }
}
