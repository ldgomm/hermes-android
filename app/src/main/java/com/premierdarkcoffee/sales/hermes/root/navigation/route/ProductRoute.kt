package com.premierdarkcoffee.sales.hermes.root.navigation.route

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.product.ProductView
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel.ChatViewModel
import com.premierdarkcoffee.sales.hermes.root.navigation.ProductRoute
import com.premierdarkcoffee.sales.hermes.root.util.function.sharedViewModel
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

fun NavGraphBuilder.productRoute(navController: NavHostController,
                                 onNavigateBackToChatViewActionTriggered: () -> Unit,
                                 onChatWithStoreButtonClicked: (Store?) -> Unit,
                                 popBackStack: () -> Unit) {

    composable<ProductRoute> { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<ChatViewModel>(navController = navController)
        val args = backStackEntry.toRoute<ProductRoute>()
        val product: Product? = Gson().fromJson(args.productJson, Product::class.java)

        val stores: Set<Store> by viewModel.stores.collectAsState()
        val store = stores.firstOrNull { it.id == product?.storeId }
        val cart by viewModel.cart.collectAsState()

        val context = LocalContext.current
        val name = SharedPreferencesHelper.getName(context)
        val latitude = SharedPreferencesHelper.getLatitude(context)
        val longitude = SharedPreferencesHelper.getLongitude(context)
        val location = GeoPoint(type = "Point", coordinates = listOf(longitude, latitude))
        val user = User(name = name ?: "Sin nombre", location)

//        LaunchedEffect(product) {
//            val storeIds = listOf(product?.storeId ?: "")
//            viewModel.getStores(storeIds)
//        }

        ProductView(product, user = user, store = store, cart = cart, onAddToCartButtonClicked = {
            viewModel.insertCartProduct(it)
            onNavigateBackToChatViewActionTriggered()
        }, onChatWithStoreIconButtonClicked = { store, message ->
            onChatWithStoreButtonClicked(store)
            viewModel.sendMessageToStore(message = message)
        }, popBackStack)
    }
}
