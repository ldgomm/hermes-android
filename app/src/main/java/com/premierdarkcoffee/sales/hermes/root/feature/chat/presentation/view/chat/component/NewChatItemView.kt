package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.hermes.R.drawable.verified

@Composable
fun NewChatItemView(
    imageRes: Int,
    title: String,
    subtitle: String,
    onNewChatButtonClicked: () -> Unit
) {
    Row(modifier = Modifier
        .clickable { onNewChatButtonClicked() }
        .fillMaxWidth()
        .background(
            Color.LightGray.copy(0.05f),
            shape = RoundedCornerShape(8.dp)
        )
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        // Image and verification icon
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(0.9f),
                        shape = CircleShape
                    )
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        // Title and subtitle
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    maxLines = 1
                )
                Icon(
                    ImageVector.vectorResource(verified),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(20.dp)
                        .offset(x = 6.dp)
                )
            }
            Text(
                subtitle,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
