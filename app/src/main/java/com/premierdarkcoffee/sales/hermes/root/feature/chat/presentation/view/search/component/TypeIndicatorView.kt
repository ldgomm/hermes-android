package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * A composable function that displays a typing indicator using animated dots.
 *
 * This function animates three dots to create a typing indicator effect. Each dot
 * is animated with a delay between them to simulate a typing motion.
 */
@Composable
fun TypingIndicatorView() {
    val dotSize = 8.dp
    val animationDuration = 300
    val delayBetweenDots = 150

    // Animatables for the vertical offset of each dot
    val dot1Offset = remember { Animatable(0f) }
    val dot2Offset = remember { Animatable(0f) }
    val dot3Offset = remember { Animatable(0f) }

    // Animation for the first dot
    LaunchedEffect(Unit) {
        dot1Offset.animateTo(targetValue = 2f,
                             animationSpec = infiniteRepeatable(animation = tween(durationMillis = animationDuration,
                                                                                  easing = LinearEasing),
                                                                repeatMode = RepeatMode.Reverse))
    }

    // Animation for the second dot with a delay
    LaunchedEffect(Unit) {
        delay(delayBetweenDots.toLong())
        dot2Offset.animateTo(targetValue = 2f,
                             animationSpec = infiniteRepeatable(animation = tween(durationMillis = animationDuration,
                                                                                  easing = LinearEasing),
                                                                repeatMode = RepeatMode.Reverse))
    }

    // Animation for the third dot with a longer delay
    LaunchedEffect(Unit) {
        delay((2 * delayBetweenDots).toLong())
        dot3Offset.animateTo(targetValue = 2f,
                             animationSpec = infiniteRepeatable(animation = tween(durationMillis = animationDuration,
                                                                                  easing = LinearEasing),
                                                                repeatMode = RepeatMode.Reverse))
    }

    // Row to display the animated dots
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        DotView(dotSize, dot1Offset.value)
        Spacer(modifier = Modifier.width(4.dp))
        DotView(dotSize, dot2Offset.value)
        Spacer(modifier = Modifier.width(4.dp))
        DotView(dotSize, dot3Offset.value)
    }
}
