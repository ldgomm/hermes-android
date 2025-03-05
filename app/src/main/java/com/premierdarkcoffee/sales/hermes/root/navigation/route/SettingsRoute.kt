package com.premierdarkcoffee.sales.hermes.root.navigation.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.settings.SettingsView
import com.premierdarkcoffee.sales.hermes.root.navigation.SettingsRoute

fun NavGraphBuilder.settingsRoute(onUserSignedOutActionTriggered: () -> Unit) {

    composable<SettingsRoute> {
        SettingsView(onUserSignedOutActionTriggered)
    }
}
