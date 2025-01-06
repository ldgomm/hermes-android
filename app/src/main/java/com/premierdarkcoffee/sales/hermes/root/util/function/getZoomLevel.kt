package com.premierdarkcoffee.sales.hermes.root.util.function

//
//  getZoomLevel.kt
//  Hermes
//
//  Created by José Ruiz on 17/8/24.
//

import kotlin.math.ln

fun getZoomLevel(distance: Int): Float {
    // Convert distance from km to meters
    val radiusInMeters = distance * 1000.0
    // Calculate the scale factor for zoom level
    val scale = radiusInMeters / 500.0
    // Calculate the zoom level based on the scale
    val zoomLevel = 16 - ln(scale) / ln(2.0)
    return zoomLevel.toFloat().coerceIn(1f, 16f)
}