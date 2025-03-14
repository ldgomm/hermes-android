package com.premierdarkcoffee.sales.hermes.root.util.function

//
//  getZoomLevel.kt
//  Hermes
//
//  Created by José Ruiz on 17/8/24.
//

import kotlin.math.ln

fun getZoomLevel(distance: Int): Float {
    val radiusInMeters = distance * 1000.0
    val scale = radiusInMeters / 500.0
    val zoomLevel = 16 - ln(scale) / ln(2.0)
    return zoomLevel.toFloat().coerceIn(1f, 20f)
}