package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.SpecificationsEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.SpecificationsDto

data class Specifications(val colours: List<String>,
                          val finished: String? = null,
                          val inBox: List<String>? = null,
                          val size: Size? = null,
                          val weight: Weight? = null) {

    fun toSpecificationsDto(): SpecificationsDto {
        return SpecificationsDto(colours = colours,
                                 finished = finished,
                                 inBox = inBox,
                                 size = size?.toSizeDto(),
                                 weight = weight?.toWeightDto())
    }

    fun toSpecificationsEntity(): SpecificationsEntity {
        return SpecificationsEntity(colours = colours,
                                    finished = finished,
                                    inBox = inBox,
                                    size = size?.toSizeEntity(),
                                    weight = weight?.toWeightEntity())
    }
}
