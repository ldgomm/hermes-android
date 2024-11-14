package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Image
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(val path: String? = null, val url: String, val belongs: Boolean) {

    fun toImage(): Image {
        return Image(path = path, url = url, belongs = belongs)
    }
}
