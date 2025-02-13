package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation

//
//  ConversationView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.ProductDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat.ChatMessage
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component.ClientMessageView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component.StoreMessageView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component.formatDayDate
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationView(user: User,
                     store: Store?,
                     messages: List<Message>,
                     onSendMessageToStoreButtonClicked: (MessageDto) -> Unit,
                     markMessageAsReadLaunchedEffect: (MessageEntity) -> Unit,
                     onProductCardClicked: (product: String) -> Unit,
                     popBackStack: () -> Unit) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val groupedMessages = groupMessagesByDay(messages).toSortedMap().map { (key, value) ->
        key to value.sortedBy { it.date }
    }

    // Automatically scroll to the bottom when new messages are added
    LaunchedEffect(messages.size) {
        coroutineScope.launch {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = store?.name ?: stringResource(R.string.no_store_name), style = MaterialTheme.typography.titleMedium)
        }, navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_description))
            }
        })
    }) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }) {
            // Messages
            Column(modifier = Modifier
                .padding(horizontal = 4.dp)
                .verticalScroll(scrollState)
                .weight(1f)) {
                groupedMessages.forEach { (day, messages) ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = day.time.formatDayDate(context),
                             style = MaterialTheme.typography.bodySmall,
                             color = MaterialTheme.colorScheme.onSurfaceVariant,
                             modifier = Modifier
                                 .padding(8.dp)
                                 .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp))
                                 .padding(horizontal = 12.dp),
                             textAlign = TextAlign.Center)
                    }

                    messages.forEach { message ->
                        if (message.fromClient) {
                            val productInfo = Gson().fromJson(message.product, ProductDto::class.java)
                            ClientMessageView(user = user,
                                              store = store,
                                              message = message,
                                              product = productInfo?.toProductInformation(),
                                              onProductCardClicked = onProductCardClicked)
                        } else {
                            StoreMessageView(message = message, markMessageAsReadLaunchedEffect = markMessageAsReadLaunchedEffect)
                        }
                    }
                }
            }

            // Input Row
            Row(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = inputText,
                                  onValueChange = { inputText = it },
                                  placeholder = {
                                      Text(text = stringResource(id = R.string.type_a_message),
                                           style = MaterialTheme.typography.bodyMedium,
                                           color = MaterialTheme.colorScheme.onSurfaceVariant)
                                  },
                                  modifier = Modifier
                                      .weight(1f)
                                      .padding(8.dp),
                                  singleLine = true,
                                  shape = RoundedCornerShape(8.dp),
                                  keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                                  keyboardActions = KeyboardActions(onSend = {
                                      messages.firstOrNull()?.let { value ->
                                          val message = MessageDto(text = inputText,
                                                                   fromClient = true,
                                                                   clientId = value.clientId,
                                                                   storeId = value.storeId)
                                          onSendMessageToStoreButtonClicked(message)
                                          inputText = ""
                                      }
                                  }))

                Spacer(modifier = Modifier.width(4.dp))

                AnimatedVisibility(visible = inputText.isNotEmpty(),
                                   enter = expandIn(expandFrom = Alignment.Center) + fadeIn(),
                                   exit = shrinkOut(shrinkTowards = Alignment.Center) + fadeOut()) {
                    IconButton(onClick = {
                        messages.firstOrNull()?.let { value ->
                            val message = MessageDto(text = inputText,
                                                     fromClient = true,
                                                     clientId = value.clientId,
                                                     storeId = value.storeId)
                            onSendMessageToStoreButtonClicked(message)
                            inputText = ""
                        }
                    }, modifier = Modifier.size(48.dp)) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send,
                             contentDescription = stringResource(R.string.send_button_description),
                             tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}


val Message.day: Date
    get() {
        // Convert the timestamp to milliseconds if needed
        val timeInterval = this.date

        // Create a Calendar instance using the default time zone
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        // Set the calendar time based on the provided timestamp
        calendar.timeInMillis = timeInterval

        // Reset the time to the start of the day
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Return the modified date
        return calendar.time
    }

val ChatMessage.day: Date
    get() {
        // Convert the timestamp to milliseconds if needed
        val timeInterval = this.date

        // Create a Calendar instance using the default time zone
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        // Set the calendar time based on the provided timestamp
        calendar.timeInMillis = timeInterval

        // Reset the time to the start of the day
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Return the modified date
        return calendar.time
    }

fun groupMessagesByDay(messages: List<Message>): Map<Date, List<Message>> {
    return messages.groupBy { it.day }
}


fun groupChatMessagesByDay(messages: List<ChatMessage>): Map<Date, List<ChatMessage>> {
    return messages.groupBy { it.day }
}

