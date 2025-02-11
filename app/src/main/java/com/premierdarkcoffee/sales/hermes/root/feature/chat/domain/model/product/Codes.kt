package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.CodesEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.CodesDto

data class Codes(val EAN: String) {

    fun toCodesDto(): CodesDto {
        return CodesDto(EAN)
    }

    fun toCodesEntity(): CodesEntity {
        return CodesEntity(EAN)
    }
}
