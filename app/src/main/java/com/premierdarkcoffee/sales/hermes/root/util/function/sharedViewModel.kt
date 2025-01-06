package com.premierdarkcoffee.sales.hermes.root.util.function

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

/**
 * Provides a shared ViewModel scoped to the parent navigation graph.
 *
 * This function is useful for sharing a ViewModel between multiple composable screens
 * that belong to the same parent navigation graph. It ensures that the ViewModel
 * instance is scoped to the parent navigation graph and is shared across composables
 * that use this function within that graph.
 *
 * @param T The type of the ViewModel.
 * @param navController The NavHostController used to manage navigation.
 * @return The shared ViewModel instance of type T.
 */
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    // Get the route of the parent destination or return a new instance of the ViewModel if there is no parent
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()

    // Remember the parent NavBackStackEntry based on the current entry
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    // Return the Hilt ViewModel scoped to the parent entry
    return hiltViewModel(parentEntry)
}
