package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Information
import androidx.room.Entity

data class InformationEntity(val id: String,
                             val title: String,
                             val subtitle: String,
                             val description: String,
                             val image: ImageEntity,
                             val place: Int) {

    fun toInformation(): Information {
        return Information(id = id,
                           title = title,
                           subtitle = subtitle,
                           description = description,
                           image = image.toImage(),
                           place = place)
    }
}