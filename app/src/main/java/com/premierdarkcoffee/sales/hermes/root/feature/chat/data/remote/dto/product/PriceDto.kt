package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Price
import kotlinx.serialization.Serializable

@Serializable
data class PriceDto(val amount: Double,
                    val currency: String = "USD",
                    val offer: OfferDto,
                    val creditCard: CreditCardDto? = null) {

    fun toPrice(): Price {
        return Price(amount = amount, currency = currency, offer = offer.toOffer(), creditCard = creditCard?.toCreditCard())
    }
}