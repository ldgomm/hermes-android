package com.premierdarkcoffee.sales.hermes.root.navigation.route

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat.ChatMessage
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.state.ChatGPTMessageState
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.SearchView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel.ChatViewModel
import com.premierdarkcoffee.sales.hermes.root.navigation.SearchRoute
import com.premierdarkcoffee.sales.hermes.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

fun NavGraphBuilder.searchRoute(
    navController: NavHostController,
    onProductCardClicked: (product: String) -> Unit,
    onNavigateToStoreMarkerClicked: (String) -> Unit,
    popBackStack: () -> Unit,
    onEditUserButtonClicked: () -> Unit
) {

    composable<SearchRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ChatViewModel>(navController = navController)
        val gptMessages: List<ChatMessage> by viewModel.gptMessages.collectAsState()
        val chatGPTMessageState: ChatGPTMessageState by viewModel.chatGPTMessageState.collectAsState()

        val stores: Set<Store> by viewModel.stores.collectAsState()
        val isTyping by viewModel.isTyping.collectAsState()

        val context = LocalContext.current
        val name = SharedPreferencesHelper.getName(context)
        val latitude = SharedPreferencesHelper.getLatitude(context)
        val longitude = SharedPreferencesHelper.getLongitude(context)
        val location = GeoPoint(type = "Point", coordinates = listOf(longitude, latitude))
        val user = User(name = name ?: "Sin nombre", location)

        LaunchedEffect(gptMessages) {
            chatGPTMessageState.messages?.let { messages ->
                val storeIds = messages.flatMap { message ->
                    val productsStoreIds = message.toChatMessage().products?.mapNotNull { it.storeId } ?: emptyList()
                    val optionalProductsStoreIds =
                        message.toChatMessage().optionalProducts?.mapNotNull { it.storeId } ?: emptyList()
                    productsStoreIds + optionalProductsStoreIds
                }
                if (storeIds.isNotEmpty()) {
                    viewModel.getStores(storeIds)
                }
            }
        }

        SearchView(stores = stores,
                   user = user,
                   chatMessages = chatGPTMessageState.messages?.map { it.toChatMessage() } ?: emptyList(),
                   isTyping = isTyping,
                   sendMessage = viewModel::sendMessage,
                   onProductCardClicked = onProductCardClicked,
                   onNavigateToStoreMarkerClicked = onNavigateToStoreMarkerClicked,
                   onDeleteChatMessagesIconButtonClicked = viewModel::deleteChatMessages,
                   popBackStack = popBackStack,
                   onEditUserButtonClicked = onEditUserButtonClicked)
    }
}
