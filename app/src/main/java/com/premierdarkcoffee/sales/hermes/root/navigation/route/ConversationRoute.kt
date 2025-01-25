package com.premierdarkcoffee.sales.hermes.root.navigation.route

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.conversation.ConversationView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel.ChatViewModel
import com.premierdarkcoffee.sales.hermes.root.navigation.ConversationRoute
import com.premierdarkcoffee.sales.hermes.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

fun NavGraphBuilder.conversationRoute(navController: NavHostController,
                                      onProductCardClicked: (product: String) -> Unit,
                                      popBackStack: () -> Unit) {

    composable<ConversationRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ChatViewModel>(navController = navController)
        val args = backStackEntry.toRoute<ConversationRoute>()
        val storeId: String = args.storeId ?: ""

        val stores by viewModel.stores.collectAsState()
        val messages by viewModel.messages.collectAsState()
        val groupedMessages = remember(messages) { messages.groupBy { it.storeId } }

        val store: Store? = stores.firstOrNull { it.id == storeId }
        val storeMessages = groupedMessages[storeId] ?: emptyList()

        val context = LocalContext.current
        val name = SharedPreferencesHelper.getName(context)
        val latitude = SharedPreferencesHelper.getLatitude(context)
        val longitude = SharedPreferencesHelper.getLongitude(context)
        val location = GeoPoint(type = "Point", coordinates = listOf(longitude, latitude))
        val user = User(name = name ?: "", location)

//        LaunchedEffect(messages) {
//            viewModel.getStores(messages.map { it.storeId })
//        }

        ConversationView(user = user,
                         store = store,
                         messages = storeMessages,
                         onSendMessageToStoreButtonClicked = viewModel::sendMessageToStore,
                         markMessageAsReadLaunchedEffect = viewModel::markMessageAsReadLaunchedEffect,
                         onProductCardClicked = onProductCardClicked,
                         popBackStack = popBackStack)

    }
}
