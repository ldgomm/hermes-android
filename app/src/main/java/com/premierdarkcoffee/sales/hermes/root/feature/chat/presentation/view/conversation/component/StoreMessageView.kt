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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageStatus
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.AUDIO
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.FILE
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.IMAGE
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.VIDEO

@Composable
fun StoreMessageView(
    message: Message,
    markMessageAsReadLaunchedEffect: (MessageEntity) -> Unit
) {
    val context = LocalContext.current

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
                    .padding(start = 8.dp)
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
                    Text(
                        text = message.text,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(8.dp),
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = message.date.formatMessageDate(context), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
            }
        }

        IMAGE -> {
            Text("Image")
        }

        VIDEO -> {
            Text("Video")
        }

        AUDIO -> {
            Text("Audio")
        }

        FILE -> {
            Text("File")
        }
    }
}
