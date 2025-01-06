package com.premierdarkcoffee.sales.hermes.root.navigation.route

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.hermes.root.navigation.SettingsRoute

fun NavGraphBuilder.settingsRoute() {

    composable<SettingsRoute> {
        Text("Pending")
    }
}
