package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import java.text.NumberFormat
import java.util.Currency
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A composable function that displays product information in a card format.
 *
 * This function shows the product image, name, description, and price details in a row layout.
 * It also handles clicks on the product card and invokes the provided callback with the product data in JSON format.
 *
 * @param product The product information to be displayed.
 * @param onProductCardClicked A callback function that is invoked when the product card is clicked.
 */
@Composable
fun ProductItemView(user: User, store: Store?, product: Product, onProductCardClicked: (productJson: String) -> Unit) {
    val context = LocalContext.current

    // Localized strings for accessibility
    val offerLabel = stringResource(id = R.string.offer_label)
    val distanceLabel = stringResource(id = R.string.distance_label)
    val discountLabel = stringResource(id = R.string.discount_label)
    val currentPriceLabel = stringResource(id = R.string.current_price_label)
    val originalPriceLabel = stringResource(id = R.string.original_price_label)

    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance(product.price.currency)
    }
    val originalPrice = product.price.amount / (1 - product.price.offer.discount / 100.0)

    ElevatedCard(onClick = { onProductCardClicked(Gson().toJson(product)) },
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(vertical = 2.dp),
                 shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Product Image
            AsyncImage(model = ImageRequest.Builder(context).data(product.image.url).crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED).memoryCachePolicy(CachePolicy.ENABLED).build(),
                       contentDescription = "${product.name} image",
                       modifier = Modifier
                           .size(72.dp)
                           .clip(RoundedCornerShape(8.dp))
                           .background(MaterialTheme.colorScheme.surfaceVariant)
                           .semantics { contentDescription = "${product.name} image" },
                       contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                Text(text = product.name.split(" ").take(2).joinToString(" "),
                     style = MaterialTheme.typography.titleMedium,
                     fontWeight = FontWeight.Bold,
                     maxLines = 2,
                     modifier = Modifier
                         .padding(bottom = 4.dp)
                         .semantics { contentDescription = product.name })
                Text(text = product.model,
                     style = MaterialTheme.typography.bodySmall,
                     fontWeight = FontWeight.Bold,
                     color = MaterialTheme.colorScheme.onSurface)
                store?.let {
                    Row {
                        Text(text = it.name,
                             style = MaterialTheme.typography.bodySmall,
                             color = MaterialTheme.colorScheme.onSurface,
                             modifier = Modifier.padding(end = 4.dp))
                        user.location.let { userLocation ->
                            store.address.location?.let { storeLocation ->
                                val distance = haversineDistance(userLocation, storeLocation)
                                Text(text = "$distanceLabel: ${"%.0f".format(distance)} km",
                                     style = MaterialTheme.typography.bodySmall,
                                     color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            // Pricing Details
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (product.price.offer.isActive) {
                    Text(text = "$offerLabel ${product.price.offer.discount}%",
                         style = MaterialTheme.typography.bodySmall,
                         color = Color.White,
                         fontWeight = FontWeight.Bold,
                         modifier = Modifier
                             .clip(RoundedCornerShape(7.dp))
                             .background(Color.Red.copy(alpha = 0.7f))
                             .padding(horizontal = 8.dp, vertical = 4.dp)
                             .semantics { contentDescription = "$discountLabel ${product.price.offer.discount}%" })
                }
                Text(text = numberFormat.format(product.price.amount),
                     style = MaterialTheme.typography.bodyLarge,
                     color = MaterialTheme.colorScheme.primary,
                     fontWeight = FontWeight.SemiBold,
                     modifier = Modifier
                         .padding(top = 4.dp)
                         .semantics { contentDescription = "$currentPriceLabel ${numberFormat.format(product.price.amount)}" })
                if (product.price.offer.isActive) {
                    Text(text = numberFormat.format(originalPrice),
                         style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough),
                         color = MaterialTheme.colorScheme.onSurfaceVariant,
                         modifier = Modifier
                             .padding(top = 2.dp)
                             .semantics { contentDescription = "$originalPriceLabel ${numberFormat.format(originalPrice)}" })
                }
            }
        }
    }
}


fun haversineDistance(userLocation: GeoPoint, storeLocation: GeoPoint): Double {
    val R = 6371.0 // Earth radius in kilometers
    val latDistance = Math.toRadians(storeLocation.coordinates[1] - userLocation.coordinates[1])
    val lonDistance = Math.toRadians(storeLocation.coordinates[0] - userLocation.coordinates[0])
    val a = sin(latDistance / 2) * sin(latDistance / 2) + cos(Math.toRadians(userLocation.coordinates[1])) * cos(Math.toRadians(
            storeLocation.coordinates[1])) * sin(lonDistance / 2) * sin(lonDistance / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}
