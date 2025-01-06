package com.premierdarkcoffee.sales.hermes.root.navigation.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.store.StoreView
import com.premierdarkcoffee.sales.hermes.root.navigation.StoreRoute

fun NavGraphBuilder.storeRoute(navController: NavHostController) {

    composable<StoreRoute> { backStackEntry ->
        val args = backStackEntry.toRoute<StoreRoute>()
        val store: Store? = Gson().fromJson(args.storeJson, Store::class.java)

        StoreView(store)

    }
}
