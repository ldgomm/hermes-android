package com.premierdarkcoffee.sales.hermes.root.navigation.route.user

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.user.UserView
import com.premierdarkcoffee.sales.hermes.root.navigation.UserRoute
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

fun NavGraphBuilder.userRoute(popBackStack: () -> Unit, onEditUserButtonClicked: () -> Unit) {

    composable<UserRoute> {
        val context = LocalContext.current
        val name = SharedPreferencesHelper.getName(context)
        val latitude = SharedPreferencesHelper.getLatitude(context)
        val longitude = SharedPreferencesHelper.getLongitude(context)
        val location = GeoPoint(type = "Point", coordinates = listOf(longitude, latitude))
        val user = User(name = name ?: "Null", location)

        Log.d(TAG, "userRoute name: $name")
        Log.d(TAG, "userRoute latitude: $latitude")
        Log.d(TAG, "userRoute longitude: $longitude")
        UserView(user, popBackStack = popBackStack, onEditUserButtonClicked = onEditUserButtonClicked)
    }
}