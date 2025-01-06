package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.WeightEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.WeightDto

data class Weight(val weight: Double, val unit: String) {

    fun toWeightDto(): WeightDto {
        return WeightDto(weight = weight, unit = unit)
    }

    fun toWeightEntity(): WeightEntity {
        return WeightEntity(weight = weight, unit = unit)
    }
}
