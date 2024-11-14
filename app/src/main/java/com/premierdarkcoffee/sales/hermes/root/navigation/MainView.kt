package com.premierdarkcoffee.sales.hermes.root.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.hermes.root.util.function.shouldShowNavigationBar

@Composable
fun MainView(
    navController: NavHostController,
    startDestination: Any
) {
    var user by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

    FirebaseAuth.getInstance().addAuthStateListener { auth ->
        user = auth.currentUser
    }

    Scaffold(bottomBar = {
        if (user != null && shouldShowNavigationBar(navController = navController)) {
            val items = listOf(ChatsRoute, SettingsRoute)
            NavigationBar(modifier = Modifier) {
                items.forEach { item ->
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    NavigationBarItem(
                        icon = {
                            Icon(imageVector = item.icon!!, contentDescription = stringResource(id = item.resourceId))
                        },
                        label = { Text(text = stringResource(item.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it == item } == true,
                        onClick = {
                            navController.navigate(item) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }) { paddingValues ->
        NavigationGraph(navController = navController, startDestination = startDestination, Modifier.padding(paddingValues))
    }
}
