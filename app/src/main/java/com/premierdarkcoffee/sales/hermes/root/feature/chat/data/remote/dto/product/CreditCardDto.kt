package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.CreditCard
import kotlinx.serialization.Serializable

@Serializable
data class CreditCardDto(val withInterest: Int, val withoutInterest: Int, val freeMonths: Int) {

    fun toCreditCard(): CreditCard {
        return CreditCard(withInterest = withInterest, withoutInterest = withoutInterest, freeMonths = freeMonths)
    }
}
