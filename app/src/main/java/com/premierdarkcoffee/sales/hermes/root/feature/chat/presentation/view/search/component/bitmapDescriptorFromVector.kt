package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

//
//  bitmapDescriptorFromVector.kt
//  Hermes
//
//  Created by José Ruiz on 3/4/25.
//

import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun bitmapDescriptorFromVector(@DrawableRes resourceId: Int): BitmapDescriptor? {
    val context = LocalContext.current
    val vectorDrawable = AppCompatResources.getDrawable(context, resourceId) ?: return null
    // For safety, set the bounds
    vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    // Create a bitmap to draw into
    val bitmap = createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    // Create a canvas to host the drawable
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
