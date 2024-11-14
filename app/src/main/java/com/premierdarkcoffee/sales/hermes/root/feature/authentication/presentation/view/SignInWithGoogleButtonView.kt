package com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.hermes.R

@Composable
fun SignInWithGoogleButtonView(
    modifier: Modifier = Modifier,
    signedInState: Boolean = false,
    primaryText: String = stringResource(id = R.string.sign_in_anonymously),
    secondaryText: String = stringResource(id = R.string.privacy_with_you_always),
    shape: CornerBasedShape = ShapeDefaults.Large,
    borderColor: Color = LightGray,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderStrokeWidth: Dp = 1.dp,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onSignInAnonymouslyButtonClicked: () -> Unit
) {

    var buttonText by remember { mutableStateOf(primaryText) }

    LaunchedEffect(key1 = signedInState) {
        buttonText = if (signedInState) secondaryText else primaryText
    }

    Surface(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .clickable(enabled = !signedInState) {
                onSignInAnonymouslyButtonClicked()
            }, shape = shape, border = BorderStroke(width = borderStrokeWidth, color = borderColor), color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .animateContentSize(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.lock_person),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = buttonText, style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize))
            if (signedInState) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(modifier = Modifier.size(17.dp), strokeWidth = 2.dp, color = progressIndicatorColor)
            }
        }
    }
}
