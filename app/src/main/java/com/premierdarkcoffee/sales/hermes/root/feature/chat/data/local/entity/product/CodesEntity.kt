package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.CodesDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Codes

data class CodesEntity(val EAN: String) {

    fun toCodesDto(): CodesDto {
        return CodesDto(EAN)
    }

    fun toCodes(): Codes {
        return Codes(EAN)
    }
}
