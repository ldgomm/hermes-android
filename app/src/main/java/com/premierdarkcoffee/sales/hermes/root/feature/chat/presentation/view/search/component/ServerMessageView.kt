package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.component.formatMessageDate
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.product.ProductItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerMessageView(
    stores: Set<Store>,
    user: User,
    message: String,
    products: List<Product>?,
    date: Long,
    onProductCardClicked: (product: String) -> Unit,
    onNavigateToStoreMarkerClicked: (String) -> Unit
) {
    val storeIds = products?.map { it.storeId } ?: emptyList()
    val matchedStores = stores.filter { it.id in storeIds }

    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    val expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
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
                // Message Text
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .clip(RoundedCornerShape(11.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(8.dp)
                        .semantics { contentDescription = message },
                    textAlign = TextAlign.Start
                )

                // Date
                Text(
                    text = date.formatMessageDate(LocalContext.current),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.width(60.dp))
        }

        // Animated visibility for the product details
        AnimatedVisibility(
            visible = expanded,
            enter = expandIn(expandFrom = Alignment.Center) + fadeIn(),
            exit = shrinkOut(shrinkTowards = Alignment.Center) + fadeOut()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    products?.forEach { product ->
                        Box(modifier = Modifier.padding(end = 4.dp)) {
                            val store: Store? = stores.firstOrNull { it.id == product.storeId }
                            ProductItemView(user = user, store = store, product = product, onProductCardClicked)
                        }
                    }
                }

                // View Stores Button
                if (!products.isNullOrEmpty()) {
                    Text(
                        text = LocalContext.current.resources.getQuantityString(
                            if (products.count() == 1) R.string.view_store_on_map else R.plurals.view_stores_on_map,
                            products.size,
                            products.size
                        ),
                        modifier = Modifier
                            .clickable { openBottomSheet = true }
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .padding(vertical = 4.dp)
                            .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                            .padding(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

    // Modal Bottom Sheet for Store Map View
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            StoresMapView(user = user, stores = matchedStores, onNavigateToStoreMarkerClicked)
        }
    }
}
