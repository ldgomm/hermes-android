package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat.ChatMessage
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component.formatMessageDate

/**
 * A composable function that displays a user's message in a chat.
 *
 * This function arranges the message text in a row, aligning it to the end of the row.
 * The message text is displayed with a background color and padding to distinguish
 * it as a user's message.
 *
 * @param message The text message to be displayed.
 */
@Composable
fun UserMessageView(message: ChatMessage) {
    // Calculate a 70% screen-width limit
    val maxBubbleWidth = (LocalConfiguration.current.screenWidthDp * 0.8f).dp

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            // Constrain the bubble to at most 70% of screen width
            .widthIn(max = maxBubbleWidth)
            // Make sure the bubble hugs its content and is anchored to the right
            .wrapContentWidth(Alignment.End), verticalArrangement = Arrangement.spacedBy(4.dp),
                // Inside the bubble, text is left-aligned
               horizontalAlignment = Alignment.Start) {
            // Message Text
            Text(text = message.firstMessage,
                 style = MaterialTheme.typography.bodyLarge,
                 color = Color.White,
                 modifier = Modifier
                     .clip(RoundedCornerShape(12.dp))
                     .background(MaterialTheme.colorScheme.primary.copy(if (isSystemInDarkTheme()) 0.2f else 0.9f))
                     .padding(8.dp)
                     .semantics { contentDescription = message.firstMessage },
                 textAlign = TextAlign.Start)

            // Message Date
            Text(text = message.date.formatMessageDate(LocalContext.current),
                 style = MaterialTheme.typography.bodySmall,
                 color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                 modifier = Modifier.align(Alignment.End))
        }
    }
}
