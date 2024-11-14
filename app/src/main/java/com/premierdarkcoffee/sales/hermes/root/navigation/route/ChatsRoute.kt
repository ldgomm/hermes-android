package com.premierdarkcoffee.sales.hermes.root.navigation.route

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.ChatsView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel.ChatViewModel
import com.premierdarkcoffee.sales.hermes.root.navigation.ChatsRoute
import com.premierdarkcoffee.sales.hermes.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

fun NavGraphBuilder.chatsRoute(
    navController: NavHostController,
    navigateToUserView: () -> Unit,
    onNewChatButtonClicked: () -> Unit,
    onConversationItemViewClicked: (String) -> Unit,
    onProductCardClicked: (productJson: String) -> Unit
) {

    composable<ChatsRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ChatViewModel>(navController = navController)

        val messages by viewModel.messages.collectAsState()
        val stores by viewModel.stores.collectAsState()
        val cart by viewModel.cart.collectAsState()

        val context = LocalContext.current
        val name = SharedPreferencesHelper.getName(context)
        val latitude = SharedPreferencesHelper.getLatitude(context)
        val longitude = SharedPreferencesHelper.getLongitude(context)
        val location = GeoPoint(type = "Point", coordinates = listOf(longitude, latitude))
        val user = User(name = name ?: "Sin nombre", location)

        LaunchedEffect(messages) {
            viewModel.getStores(messages.map { it.storeId })
        }

        ChatsView(
            stores = stores,
            messages = messages,
            user = user,
            cart = cart,
            navigateToUserView = navigateToUserView,
            onNewChatButtonClicked = onNewChatButtonClicked,
            onConversationItemViewClicked = onConversationItemViewClicked,
            onProductCardClicked = onProductCardClicked,
            onDeleteProductSwiped = viewModel::deleteCartProduct
        )
    }
}
