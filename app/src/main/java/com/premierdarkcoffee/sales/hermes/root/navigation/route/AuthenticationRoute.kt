package com.premierdarkcoffee.sales.hermes.root.navigation.route

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.view.AuthenticationView
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.viewmodel.AuthenticationViewModel
import com.premierdarkcoffee.sales.hermes.root.navigation.AuthenticationRoute
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

fun NavGraphBuilder.authenticationRoute(onNavigateToProductsViewTriggered: () -> Unit) {

    composable<AuthenticationRoute> {
        val viewModel: AuthenticationViewModel = hiltViewModel()

        val state by viewModel.state
        val context = LocalContext.current

        AuthenticationView(state = state, onSignInAnonymouslyButtonClicked = {
            viewModel.signInAnonymously(onSuccess = { userName ->
                SharedPreferencesHelper.setName(context, userName)
                SharedPreferencesHelper.setLatitude(context, latitude = -0.182847)
                SharedPreferencesHelper.setLongitude(context, longitude = -78.484498)
                onNavigateToProductsViewTriggered()
            }, onFailure = {
                Log.d(TAG, "authenticationRoute: Error ${it.message}")
            })
        })
    }
}
