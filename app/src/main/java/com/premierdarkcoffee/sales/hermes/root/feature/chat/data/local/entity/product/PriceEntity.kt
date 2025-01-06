package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Price
import androidx.room.Entity

data class PriceEntity(val amount: Double,
                       val currency: String = "USD",
                       val offer: OfferEntity,
                       val creditCard: CreditCardEntity? = null) {

    fun toPrice(): Price {
        return Price(amount = amount, currency = currency, offer = offer.toOffer(), creditCard = creditCard?.toCreditCard())
    }
}
