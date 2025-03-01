package com.premierdarkcoffee.sales.hermes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel.storeApiKey
import com.premierdarkcoffee.sales.hermes.root.navigation.AuthenticationRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.ChatsRoute
import com.premierdarkcoffee.sales.hermes.root.navigation.NavigationGraph
import com.premierdarkcoffee.sales.hermes.root.util.theme.HermesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = BuildConfig.API_KEY
        storeApiKey(this, apiKey)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        networkMonitor = NetworkMonitor(this)

        setContent {
            val navController = rememberNavController()
            val user = FirebaseAuth.getInstance().currentUser
            val startDestination = if (user != null) ChatsRoute else AuthenticationRoute
            val isConnected by networkMonitor.observeAsState(true)

            HermesTheme {
                when {
                    !isConnected -> NoInternetView()
                    user != null -> NavigationGraph(navController, startDestination)
                    else -> {
                        UnstableConnectionView()
                    }
                }
            }
        }
    }
}
