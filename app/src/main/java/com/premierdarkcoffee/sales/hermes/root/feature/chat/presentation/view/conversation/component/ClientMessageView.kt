package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component

//
//  ClientMessageItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.AUDIO
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.FILE
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.IMAGE
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.TEXT
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageType.VIDEO
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.product.ProductItemView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun ClientMessageView(
    user: User,
    store: Store?,
    message: Message,
    product: Product?,
    onProductCardClicked: (product: String) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        when (message.type) {
            TEXT -> {
                Spacer(Modifier.padding(horizontal = 60.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .wrapContentWidth(Alignment.End),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = message.text, fontSize = 16.sp, modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                if (product != null) {
                                    expanded = !expanded
                                }
                            }
                            .background(
                                MaterialTheme.colorScheme.primary.copy(
                                    if (isSystemInDarkTheme()) 0.2f else 0.9f
                                )
                            )
                            .padding(8.dp), color = Color.White, textAlign = TextAlign.Start)

                        Row(
                            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = message.date.formatMessageDate(context), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
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


        // Animated visibility for the product details
        AnimatedVisibility(
            visible = expanded && product != null,
            enter = expandVertically(animationSpec = tween(durationMillis = 500)) + fadeIn(),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 500)) + fadeOut()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
//                    .horizontalScroll(rememberScrollState())
                , horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                    product?.let {
                        ProductItemView(
                            user = user, store = store, product = product, onProductCardClicked
                        )
                    }
                }
            }
        }
    }
}

fun Long.formatMessageDate(context: Context): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - this

    val oneMinute = 60 * 1000
    val oneHour = 60 * oneMinute
    val oneDay = 24 * oneHour

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentTime

    val currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    calendar.timeInMillis = this
    val messageDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    return when {
        diff < oneMinute -> "Just now"
        diff < oneHour -> "${diff / oneMinute} min ago"
        diff < oneDay && currentDayOfYear == messageDayOfYear -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "HH:mm" else "hh:mm a"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }

        diff < 2 * oneDay && currentDayOfYear - messageDayOfYear == 1 -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "HH:mm" else "hh:mm a"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }

        else -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "MMM d, yyyy, HH:mm" else "MMM d, yyyy, hh:mm a"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }
    }
}


fun Long.formatConversationDate(context: Context): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - this

    val oneMinute = 60 * 1000
    val oneHour = 60 * oneMinute
    val oneDay = 24 * oneHour
    val oneWeek = 7 * oneDay
    val oneMonth = 30L * oneDay
    val oneYear = 365L * oneDay

    return when {
        diff < oneMinute -> "now"
        diff < oneHour -> "${diff / oneMinute} min ago"
        diff < oneDay -> "${diff / oneHour} hours ago"
        diff < 2 * oneDay -> "Yesterday"
        diff < oneWeek -> "${diff / oneDay} days ago"
        diff < oneMonth -> "${diff / oneWeek} weeks ago"
        diff < oneYear -> "${diff / oneMonth} months ago"
        else -> {
            val date = Date(this)
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "MMM d, yyyy, HH:mm" else "MMM d, yyyy, hh:mm a"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(date)
        }
    }
}

fun Long.formatDayDate(context: Context): String {
    val currentTime = System.currentTimeMillis()

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentTime

    val currentYear = calendar.get(Calendar.YEAR)
    val currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    calendar.timeInMillis = this
    val messageYear = calendar.get(Calendar.YEAR)
    val messageDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    return when {
        currentDayOfYear == messageDayOfYear && currentYear == messageYear -> "Today"
        currentDayOfYear - messageDayOfYear == 1 && currentYear == messageYear -> "Yesterday"
        else -> {
            val is24Hour = DateFormat.is24HourFormat(context)
            val pattern = if (is24Hour) "MMM d, yyyy" else "MMM d, yyyy"
            val dateFormatter = SimpleDateFormat(
                pattern, Locale.getDefault()
            )
            dateFormatter.format(Date(this))
        }
    }
}
