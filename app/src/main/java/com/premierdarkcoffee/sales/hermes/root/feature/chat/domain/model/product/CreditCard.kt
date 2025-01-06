package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.CreditCardEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.CreditCardDto

data class CreditCard(val withInterest: Int, val withoutInterest: Int, val freeMonths: Int) {

    fun toCreditCardDto(): CreditCardDto {
        return CreditCardDto(withInterest = withInterest, withoutInterest = withoutInterest, freeMonths = freeMonths)
    }

    fun toCreditCardEntity(): CreditCardEntity {
        return CreditCardEntity(withInterest = withInterest, withoutInterest = withoutInterest, freeMonths = freeMonths)
    }
}
