package com.premierdarkcoffee.sales.hermes.root.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.premierdarkcoffee.sales.hermes.R.string.chats
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class Route(@StringRes val resourceId: Int, @Contextual val icon: ImageVector? = null)

@Serializable
object AuthenticationRoute

@Serializable
data object ChatsRoute : Route(resourceId = chats, icon = Icons.Sharp.Menu)

@Serializable
data class SearchRoute(val chat: String? = null)

@Serializable
data class ConversationRoute(val storeId: String? = null)

@Serializable
data class ProductRoute(val productJson: String? = null)

@Serializable
object SettingsRoute

@Serializable
object UserRoute

@Serializable
object EditUserRoute

@Serializable
data class StoreRoute(val storeJson: String? = null)
