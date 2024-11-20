package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.product

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.R.drawable.storefront
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.titleStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A composable function that displays detailed product information.
 *
 * This function shows the product's image, description, price, specifications, overview, and warranty information.
 * It also provides options to add the product to the cart and to chat with the store.
 *
 * @param product The detailed information about the product.
 * @param cart The list of products currently in the cart.
 * @param onAddToCartButtonClicked A callback function that is invoked when the add to cart button is clicked.
 * @param onChatWithStoreIconButtonClicked A callback function that is invoked when the chat with store button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductView(
    product: Product?,
    user: User,
    store: Store?,
    cart: List<Product>,
    onAddToCartButtonClicked: (Product) -> Unit,
    onChatWithStoreIconButtonClicked: (Store?, MessageDto) -> Unit,
    popBackStack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var showRequestDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    if (product == null) return
    val messageToStore = stringResource(id = R.string.message_to_store)

    var isStoreImageExpanded by remember { mutableStateOf(false) }
    val imageSize by animateDpAsState(targetValue = if (isStoreImageExpanded) 500.dp else 40.dp, label = "")
    val imagePadding by animateDpAsState(targetValue = if (isStoreImageExpanded) 0.dp else 10.dp, label = "")
    val cornerShape by animateDpAsState(targetValue = if (isStoreImageExpanded) 0.dp else 7.dp, label = "")
    val alpha by animateFloatAsState(targetValue = if (isStoreImageExpanded) 1f else 0.2f, label = "")


    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = product.name, style = titleStyle)
        }, navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back), contentDescription = "")
            }
        }, actions = {
            IconButton(onClick = { showRequestDialog = true }) {
                Icon(imageVector = ImageVector.vectorResource(storefront), contentDescription = null)
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Main Product Image and Store Image (expandable)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                AsyncImage(
                    model = product.image.url,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    contentScale = ContentScale.FillBounds
                )

                // Store Image with Expand Effect
                store?.image?.url?.let { storeImageUrl ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                isStoreImageExpanded = !isStoreImageExpanded
                                if (isStoreImageExpanded) {
                                    coroutineScope.launch {
                                        delay(3000)
                                        isStoreImageExpanded = false
                                    }
                                }
                            }
                    ) {
                        AsyncImage(
                            model = storeImageUrl,
                            contentDescription = store.name,
                            modifier = Modifier
                                .padding(imagePadding)
                                .size(imageSize)
                                .clip(RoundedCornerShape(cornerShape))
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }

                Text(
                    text = if (isStoreImageExpanded) store?.name ?: "" else product.category.subclass,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 12.dp)
                        .background(Color.Gray.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .align(Alignment.BottomEnd),
                    textAlign = TextAlign.End
                )
            }

            // Label, publisher, year
            SectionView(title = stringResource(id = R.string.label_label)) {
                Text(
                    text = product.label ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }


            if (product.year != null) {
                SectionView(title = stringResource(id = R.string.owner_label)) {
                    Text(
                        text = "${product.owner}, ${product.year}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            } else {
                SectionView(title = stringResource(id = R.string.owner_label)) {
                    Text(
                        text = product.owner ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            if (product.model.length > 3) {
                SectionView(title = stringResource(id = R.string.model_label)) {
                    Text(
                        text = product.model,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

// Description
            SectionView(title = stringResource(id = R.string.description_label)) {
                Row(
                    modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }


            product.overview.let { overviewList ->
                if (overviewList.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp)
                    ) {
                        overviewList.forEach { info ->
                            ElevatedCard(
                                modifier = Modifier
                                    .width(300.dp) // Set a fixed width for each card to make them more consistent in size
                                    .padding(end = 16.dp),
                                shape = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.elevatedCardElevation(4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    AsyncImage(
                                        model = info.image.url,
                                        contentDescription = info.title,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                            .clip(MaterialTheme.shapes.medium)
                                            .padding(bottom = 8.dp), // Reduced padding for better content alignment
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = info.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 4.dp) // Reduced padding for a more compact look
                                    )
                                    Text(
                                        text = info.subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp) // Reduced padding for a more compact look
                                    )
                                    Text(
                                        text = info.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 10, // Limited lines to make the card more compact
                                        overflow = TextOverflow.Ellipsis // Handle overflow with ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            SectionView(title = stringResource(id = R.string.details_label)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
                ) {
                    ProductDetailRow(
                        label = stringResource(id = R.string.price_label),
                        value = "${product.price.amount} ${product.price.currency}"
                    )
                    ProductDetailRow(
                        label = stringResource(id = R.string.stock_label), value = "${product.stock}"
                    )
                    ProductDetailRow(
                        label = stringResource(id = R.string.origin_label), value = product.origin
                    )
                }
            }

            product.specifications?.let { specifications ->
                SectionView(title = stringResource(id = R.string.specifications_label)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
                    ) {
                        ProductDetailRow(
                            label = stringResource(id = R.string.colours_label), value = specifications.colours.joinToString(", ")
                        )
                        specifications.finished?.let { finished ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.finished_label), value = finished
                            )
                        }
                        specifications.inBox?.let { inBox ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.in_box_label), value = inBox.joinToString(", ")
                            )
                        }
                        specifications.size?.let { size ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.size_label),
                                value = "${size.width}x${size.height}x${size.depth} ${size.unit}"
                            )
                        }
                        specifications.weight?.let { weight ->
                            ProductDetailRow(
                                label = stringResource(id = R.string.weight_label), value = "${weight.weight} ${weight.unit}"
                            )
                        }
                    }
                }
            }

            product.warranty?.let { warranty ->
                SectionView(title = stringResource(id = R.string.warranty_label)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
                    ) {
                        ProductDetailRow(
                            label = stringResource(id = R.string.warranty_duration_label, warranty.months),
                            value = warranty.details.joinToString(", ")
                        )
                    }
                }
            }

            product.legal?.let { legal ->
                SectionView(title = stringResource(id = R.string.legal_label)) {
                    Text(
                        text = legal,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            product.warning?.let { warning ->
                SectionView(title = stringResource(id = R.string.warning_label)) {
                    Text(
                        text = warning,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }

    // Dialog for confirming addition to cart
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, title = {
            Text(text = stringResource(id = R.string.add_to_cart), style = MaterialTheme.typography.titleLarge)
        }, text = {
            Text(text = stringResource(id = R.string.confirm_add_to_cart), style = MaterialTheme.typography.bodyMedium)
        }, confirmButton = {
            Button(onClick = {
                onAddToCartButtonClicked(product)
                showDialog = false
            }) {
                Text(text = stringResource(id = R.string.yes))
            }
        }, dismissButton = {
            Button(onClick = { showDialog = false }) {
                Text(text = stringResource(id = R.string.no))
            }
        })
    }


    if (showRequestDialog) {
        AlertDialog(onDismissRequest = { showRequestDialog = false }, title = {
            Text(
                text = stringResource(id = R.string.request_product_from_store, product.name, store?.name ?: ""),
                style = MaterialTheme.typography.titleLarge
            )
        }, text = {
            Text(
                text = stringResource(id = R.string.confirm_request_product),
            )
        }, confirmButton = {
            Button(onClick = {
                showRequestDialog = false
                product.storeId?.let {
                    val message = MessageDto(
                        text = "Hola, ${store?.name}, $messageToStore: ${product.name}",
                        clientId = FirebaseAuth.getInstance().uid ?: "",
                        storeId = product.storeId,
                        product = Gson().toJson(product)
                    )
                    onChatWithStoreIconButtonClicked(store, message)
                }
            }) {
                Text(text = stringResource(id = R.string.yes))
            }
        }, dismissButton = {
            Button(onClick = { showRequestDialog = false }) {
                Text(text = stringResource(id = R.string.no))
            }
        })
    }

}

@Composable
fun ProductDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

//            if (!cart.contains(product)) {
//                IconButton(onClick = { showDialog = true }) {
//                    Icon(imageVector = ImageVector.vectorResource(add_shopping_cart), contentDescription = null)
//                }
//            }