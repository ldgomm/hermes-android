package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.MessageStatus
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.component.ConversationItemView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.component.NewChatItemView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.product.ProductItemView

/**
 * A composable function that displays a list of chat messages grouped by store.
 *
 * This function uses a scaffold with a top app bar and a lazy column to display pinned messages,
 * a new chat item, and grouped chat messages from different stores. It provides options to create
 * a new chat and to view conversations with specific stores.
 *
 * @param messages The list of chat messages to be displayed.
 * @param onNewChatButtonClicked A callback function that is invoked when the new chat button is clicked.
 * @param onConversationItemViewClicked A callback function that is invoked when a conversation item is clicked.
 */
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun ChatsView(
    messages: List<Message>,
    stores: Set<Store>,
    user: User,
    cart: List<Product>,
    navigateToUserView: () -> Unit,
    onNewChatButtonClicked: () -> Unit,
    onConversationItemViewClicked: (String) -> Unit,
    onProductCardClicked: (productJson: String) -> Unit,
    onDeleteProductSwiped: (productId: String) -> Unit
) {

    val sortedGroupedMessages = remember(messages) {
        messages.groupBy { it.storeId }.mapValues { entry -> entry.value.sortedBy { it.date } }.toList()
            .sortedByDescending { it.second.lastOrNull()?.date }
    }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.chats),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = navigateToUserView) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.person_outline),
                            contentDescription = stringResource(id = R.string.user_profile_icon_description)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Pinned Chat Section
            item {
                Text(
                    text = stringResource(id = R.string.pinned_chat),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
            item {
                NewChatItemView(
                    imageRes = R.drawable.auto_awesome,
                    title = stringResource(id = R.string.chat_with_chatgpt),
                    subtitle = stringResource(id = R.string.tech_inquiries_and_stock_availability),
                    onNewChatButtonClicked = onNewChatButtonClicked
                )
            }

            // Spacer for Separation
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Store Chats Section
            item {
                Text(
                    text = stringResource(id = R.string.stores_label),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Store Conversations
            sortedGroupedMessages.forEach { (storeId, storeMessages) ->
                item {
                    val lastMessage = storeMessages.lastOrNull()
                    lastMessage?.let { message ->
                        val sentOrDeliveredCount = storeMessages.count {
                            (it.status == MessageStatus.SENT || it.status == MessageStatus.DELIVERED) && !it.fromClient
                        }
                        val store = stores.firstOrNull { it.id == storeId }
                        ConversationItemView(
                            store = store,
                            message = message,
                            sentOrDeliveredCount = sentOrDeliveredCount,
                            onConversationItemViewClicked = {
                                onConversationItemViewClicked(storeId)
                            }
                        )
                    }
                }
            }
        }
    }
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Empty cart message
                if (cart.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = R.string.cart_is_empty),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier
                                .padding(16.dp)
                                .background(
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        )
                    }
                }

                // Products in cart
                items(cart) { product ->
                    val dismissState = rememberDismissState(confirmStateChange = {
                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                            onDeleteProductSwiped(product.id)
                            true
                        } else {
                            false
                        }
                    })

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color = when (dismissState.targetValue) {
                                DismissValue.Default -> MaterialTheme.colorScheme.surface
                                DismissValue.DismissedToEnd -> Color.Red
                                DismissValue.DismissedToStart -> Color.Red
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp),
                                contentAlignment = if (direction == DismissDirection.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(id = R.string.delete_product_icon_description),
                                    tint = Color.White
                                )
                            }
                        },
                        dismissContent = {
                            val store = stores.firstOrNull { it.id == product.storeId }
                            ProductItemView(
                                user = user,
                                store = store,
                                product = product,
                                onProductCardClicked = onProductCardClicked
                            )
                        }
                    )
                }
            }
        }
    }

}

val titleStyle: TextStyle = TextStyle(
    fontSize = 27.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal
)
