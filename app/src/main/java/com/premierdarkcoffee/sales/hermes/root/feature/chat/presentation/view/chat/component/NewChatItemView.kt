package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.hermes.R

@Composable
fun NewChatItemView(
    imageRes: Int,
    title: String,
    subtitle: String,
    onNewChatButtonClicked: () -> Unit
) {
    // Localized strings for accessibility
    val chatImageDescription = stringResource(id = R.string.chat_image_description)
    val verifiedIconDescription = stringResource(id = R.string.verified_icon_description)

    Row(
        modifier = Modifier
            .clickable { onNewChatButtonClicked() }
            .fillMaxWidth()
            .background(
                color = Color.LightGray.copy(alpha = 0.05f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image with accessible content description
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = chatImageDescription,
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    shape = CircleShape
                )
                .padding(8.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Title and subtitle with verified icon
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.semantics {
                        contentDescription = title
                    }
                )

                // Verified Icon
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.verified),
                    contentDescription = verifiedIconDescription,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(20.dp)
                        .offset(x = 6.dp)
                )
            }

            // Subtitle
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics {
                    contentDescription = subtitle
                }
            )
        }
    }
}
