package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
            .wrapContentWidth(Alignment.End),
        horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = message.firstMessage,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        MaterialTheme.colorScheme.primary.copy(
                            if (isSystemInDarkTheme()) 0.2f else 0.9f
                        )
                    )
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = message.date.formatMessageDate(LocalContext.current),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.align(Alignment.End)
            )
        }

    }
}
