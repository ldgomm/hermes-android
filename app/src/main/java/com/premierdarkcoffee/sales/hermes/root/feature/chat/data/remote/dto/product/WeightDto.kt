package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Weight
import kotlinx.serialization.Serializable

@Serializable
data class WeightDto(val weight: Double, val unit: String) {

    fun toWeight(): Weight {
        return Weight(weight = weight, unit = unit)
    }
}
