package com.premierdarkcoffee.sales.hermes.root.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.premierdarkcoffee.sales.hermes.root.navigation.route.authenticationRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.chatsRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.conversationRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.productRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.searchRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.settingsRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.storeRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.user.editUserRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.route.user.userRoute

@Composable
fun NavigationGraph(navController: NavHostController, startDestination: Any, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        authenticationRoute(onNavigateToProductsViewTriggered = {
            navController.popBackStack()
            navController.navigate(ChatsRoute)
        })

        chatsRoute(navController = navController, navigateToUserView = {
            navController.navigate(UserRoute)
        }, onNewChatButtonClicked = {
            navController.navigate(SearchRoute(chat = null))
        }, onConversationItemViewClicked = { storeId: String ->
            navController.navigate(ConversationRoute(storeId))
        }, onProductCardClicked = { productJson ->
            navController.navigate(ProductRoute(productJson))
        })

        searchRoute(navController = navController, onProductCardClicked = { productJson ->
            navController.navigate(ProductRoute(productJson))
        }, onNavigateToStoreMarkerClicked = { storeJson ->
            navController.navigate(StoreRoute(storeJson))
        }, popBackStack = { navController.popBackStack() }, onEditUserButtonClicked = { navController.navigate(EditUserRoute) })

        productRoute(navController = navController, onNavigateBackToChatViewActionTriggered = {
            navController.popBackStack()
        }, onChatWithStoreButtonClicked = { store ->
            navController.popBackStack()
            navController.popBackStack()
            if (store != null) {
                navController.navigate(ConversationRoute(store.id))
            }
        }, popBackStack = { navController.popBackStack() })

        conversationRoute(navController = navController, onProductCardClicked = { productJson ->
            navController.navigate(ProductRoute(productJson))
        }, popBackStack = { navController.popBackStack() })

        userRoute(popBackStack = { navController.popBackStack() },
                  onEditUserButtonClicked = { navController.navigate(EditUserRoute) })
        editUserRoute(popBackStack = { navController.popBackStack() })

        storeRoute(navController = navController)

        settingsRoute()
    }
}
