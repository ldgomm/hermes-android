package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.CreditCard
import androidx.room.Entity

data class CreditCardEntity(val withInterest: Int, val withoutInterest: Int, val freeMonths: Int) {

    fun toCreditCard(): CreditCard {
        return CreditCard(withInterest = withInterest, withoutInterest = withoutInterest, freeMonths = freeMonths)
    }
}
