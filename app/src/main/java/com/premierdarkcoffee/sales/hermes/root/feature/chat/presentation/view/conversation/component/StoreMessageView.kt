package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component

//
//  StoreMessageItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageStatus
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType

@Composable
fun StoreMessageView(
    message: Message,
    markMessageAsReadLaunchedEffect: (MessageEntity) -> Unit
) {
    val context = LocalContext.current

    // Localized strings for message types
    val textMessageLabel = stringResource(id = R.string.text_message_label)
    val imageMessageLabel = stringResource(id = R.string.image_message_label)
    val videoMessageLabel = stringResource(id = R.string.video_message_label)
    val audioMessageLabel = stringResource(id = R.string.audio_message_label)
    val fileMessageLabel = stringResource(id = R.string.file_message_label)

    LaunchedEffect(message.status != MessageStatus.READ) {
        if (message.status != MessageStatus.READ) {
            markMessageAsReadLaunchedEffect(message.toMessageEntity())
        }
    }

    when (message.type) {
        MessageType.TEXT -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start)
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    // Message text with accessibility
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(8.dp)
                            .semantics { contentDescription = "$textMessageLabel: ${message.text}" },
                        textAlign = TextAlign.Start
                    )

                    // Message date with accessibility
                    Text(
                        text = message.date.formatMessageDate(context),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.semantics {
                            contentDescription = message.date.formatMessageDate(context)
                        }
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
            }
        }

        MessageType.IMAGE -> {
            Text(
                text = imageMessageLabel,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics { contentDescription = imageMessageLabel }
            )
        }

        MessageType.VIDEO -> {
            Text(
                text = videoMessageLabel,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics { contentDescription = videoMessageLabel }
            )
        }

        MessageType.AUDIO -> {
            Text(
                text = audioMessageLabel,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics { contentDescription = audioMessageLabel }
            )
        }

        MessageType.FILE -> {
            Text(
                text = fileMessageLabel,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics { contentDescription = fileMessageLabel }
            )
        }
    }
}
