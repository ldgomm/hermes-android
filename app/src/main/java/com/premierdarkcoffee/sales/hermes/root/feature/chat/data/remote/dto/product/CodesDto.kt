package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.CodesEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Codes
import kotlinx.serialization.Serializable

@Serializable
data class CodesDto(val EAN: String) {

    fun toCodes(): Codes {
        return Codes(EAN)
    }

    fun toCodesEntity(): CodesEntity {
        return CodesEntity(EAN)
    }
}
