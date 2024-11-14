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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.R.drawable.storefront
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ConversationItemView(
    store: Store?,
    message: Message,
    sentOrDeliveredCount: Int,
    onConversationItemViewClicked: () -> Unit
) {
    Row(modifier = Modifier
        .padding(vertical = 4.dp)
        .clickable { onConversationItemViewClicked() }

        .fillMaxWidth()
        .background(
            Color.LightGray.copy(0.05f), shape = RoundedCornerShape(8.dp)
        )
        .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

        // Store icon
        store?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(store.image.url)
                    .crossfade(true)  // Enables a crossfade animation when the image loads
                    .diskCachePolicy(CachePolicy.ENABLED) // Enables disk caching
                    .memoryCachePolicy(CachePolicy.ENABLED) // Enables memory caching
                    .build(),
                contentDescription = store.name,
                modifier = Modifier
                    .size(54.dp) // Increased size for larger image
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Image(
                painterResource(storefront),
                "Storefront",
                Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Message content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = store?.name ?: stringResource(id = R.string.no_store_name), fontSize = 17.sp, fontWeight = FontWeight.Bold
            )
            Text(
                text = message.text, fontSize = 14.sp, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            // Date text
            Text(
                text = message.date.formatShortDate(),
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(end = 10.dp)
            )

            // Message count badge
            if (sentOrDeliveredCount > 0) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.DarkGray.copy(0.7f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$sentOrDeliveredCount",
                        fontSize = 10.sp, // Slightly larger text size
                        color = Color.White
                    )
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
