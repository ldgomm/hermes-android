package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ImageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.ImageDto

data class Image(val path: String? = null, val url: String, val belongs: Boolean) {

    fun toImageDto(): ImageDto {
        return ImageDto(path = path, url = url, belongs = belongs)
    }

    fun toImageEntity(): ImageEntity {
        return ImageEntity(path = path, url = url, belongs = belongs)
    }
}
