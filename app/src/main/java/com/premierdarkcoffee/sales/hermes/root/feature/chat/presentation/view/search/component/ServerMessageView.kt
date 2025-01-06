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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                Text(
                    text = message,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(11.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(8.dp),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = date.formatMessageDate(LocalContext.current),
                    fontSize = 12.sp,
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
//                val configuration = LocalConfiguration.current
//                val screenWidth = configuration.screenWidthDp.dp
//
//                LazyRow(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    items(products ?: emptyList()) { product ->
//                        val store: Store? = stores.firstOrNull { it.id == product.storeId }
//                        Box(
//                            modifier = Modifier
//                                .width(screenWidth * 0.9f).height(150.dp)
//                                .padding(end = 8.dp)
//                        ) {
//                            ProductItemView(
//                                user = user, store = store, product = product, onProductCardClicked = onProductCardClicked
//                            )
//                        }
//                    }
//                }

                if (products?.isNotEmpty() == true) {
                    Text(text = stringResource(id = if (products.count() == 1) R.string.view_store_on_map else R.string.view_stores_on_map),
                         modifier = Modifier
                             .clickable { openBottomSheet = true }
                             .fillMaxWidth(1f)
                             .wrapContentWidth(Alignment.CenterHorizontally)
                             .padding(vertical = 4.dp)
                             .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                             .padding(2.dp)
                             .padding(horizontal = 10.dp),
                         color = MaterialTheme.colorScheme.primary,
                         fontSize = 12.sp)
                }
            }
        }
    }

    if (openBottomSheet) {
        ModalBottomSheet(onDismissRequest = { openBottomSheet = false }, sheetState = bottomSheetState) {
            StoresMapView(user = user, stores = matchedStores, onNavigateToStoreMarkerClicked)
        }
    }
}
