package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Size
import androidx.room.Entity

data class SizeEntity(val width: Double, val height: Double, val depth: Double, val unit: String) {

    fun toSize(): Size {
        return Size(width = width, height = height, depth = depth, unit = unit)
    }
}
