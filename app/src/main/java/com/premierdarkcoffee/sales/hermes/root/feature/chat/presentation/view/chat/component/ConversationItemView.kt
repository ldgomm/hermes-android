package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.component

//
//  ConversationItemView.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ConversationItemView(store: Store?, message: Message, sentOrDeliveredCount: Int, onConversationItemViewClicked: () -> Unit) {
    // Localized strings for accessibility
    val storeImageDescription =
        stringResource(id = R.string.store_image_description, store?.name ?: stringResource(id = R.string.no_store_name))
    val noStoreNameDescription = stringResource(id = R.string.no_store_name)
    val messageDateDescription = stringResource(id = R.string.message_date_label)

    Row(modifier = Modifier
        .padding(vertical = 4.dp)
        .clickable { onConversationItemViewClicked() }
        .fillMaxWidth()
        .background(color = Color.LightGray.copy(alpha = 0.05f), shape = RoundedCornerShape(8.dp))
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        // Store icon
        store?.let {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(store.image.url).crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED).memoryCachePolicy(CachePolicy.ENABLED).build(),
                       contentDescription = storeImageDescription,
                       modifier = Modifier
                           .size(54.dp)
                           .clip(CircleShape)
                           .background(MaterialTheme.colorScheme.surfaceVariant),
                       contentScale = ContentScale.Crop)
        } ?: run {
            Image(painter = painterResource(id = R.drawable.storefront),
                  contentDescription = noStoreNameDescription,
                  modifier = Modifier
                      .size(54.dp)
                      .clip(CircleShape)
                      .background(MaterialTheme.colorScheme.surfaceVariant)
                      .padding(12.dp))
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Message content
        Column(modifier = Modifier.weight(1f)) {
            // Store Name
            Text(text = store?.name ?: stringResource(id = R.string.no_store_name),
                 style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                 modifier = Modifier.semantics { contentDescription = store?.name ?: noStoreNameDescription })

            // Message Text
            Text(text = message.text,
                 style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                 maxLines = 2,
                 overflow = TextOverflow.Ellipsis,
                 modifier = Modifier.semantics { contentDescription = message.text })
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Date and Badge
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            // Date
            Text(text = message.date.formatShortDate(),
                 style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                 modifier = Modifier
                     .padding(end = 10.dp)
                     .semantics { contentDescription = "$messageDateDescription: ${message.date.formatShortDate()}" })

            // Message Count Badge
            if (sentOrDeliveredCount > 0) {
                Box(modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.DarkGray.copy(alpha = 0.7f), shape = CircleShape),
                    contentAlignment = Alignment.Center) {
                    Text(text = "$sentOrDeliveredCount",
                         style = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                         modifier = Modifier.semantics { contentDescription = "$sentOrDeliveredCount messages pending" })
                }
            }
        }
    }
}


fun Long.formatShortDate(): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - this

    val oneMinute = 60 * 1000
    val oneHour = 60 * oneMinute
    val oneDay = 24 * oneHour

    return when {
        diff < oneDay -> { // If the difference is less than a day, show hours and minutes
            val formatter = SimpleDateFormat("hh:mm", Locale.getDefault())
            formatter.format(Date(this))
        }

        diff < 2 * oneDay -> { // If the difference is less than two days, show 'yesterday'
            "yesterday"
        }

        diff < 7 * oneDay -> { // If the difference is less than a week, show the day of the week
            val formatter = SimpleDateFormat("EEE", Locale.getDefault())
            formatter.format(Date(this))
        }

        diff < 30 * oneDay -> { // If the difference is less than a month, show 'last week'
            "last week"
        }

        diff < 365 * oneDay -> { // If the difference is less than a year, show the month
            val formatter = SimpleDateFormat("MM", Locale.getDefault())
            formatter.format(Date(this))
        }

        else -> { // If the difference is more than a year, show the month and year
            val formatter = SimpleDateFormat("MM yy", Locale.getDefault())
            formatter.format(Date(this))
        }
    }
}
