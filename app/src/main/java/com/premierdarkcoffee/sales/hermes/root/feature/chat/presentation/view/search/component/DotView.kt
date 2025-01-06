package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a dot with a specified size and vertical offset.
 *
 * This function creates a circular dot with a given size and applies a vertical offset to it.
 * The dot is styled with the primary color of the current theme.
 *
 * @param size The diameter of the dot.
 * @param offset The vertical offset to be applied to the dot.
 */
@Composable
fun DotView(
    size: Dp,
    offset: Float
) {
    Box(
        modifier = Modifier
            .size(size) // Set the size of the dot
            .offset(y = offset.dp) // Apply the vertical offset
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape) // Set the background color and shape
    )
}
