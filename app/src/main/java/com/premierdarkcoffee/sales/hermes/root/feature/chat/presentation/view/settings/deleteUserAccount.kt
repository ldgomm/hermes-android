package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.settings

//
//  deleteUserAccount.kt
//  Hermes
//
//  Created by José Ruiz on 1/4/25.
//

import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

suspend fun deleteUserAccount(user: FirebaseUser, onSuccess: () -> Unit, onError: (String) -> Unit, onReauthNeeded: () -> Unit) {
    try {
        user.delete().await()
        onSuccess()
    } catch (e: FirebaseAuthRecentLoginRequiredException) {
        onReauthNeeded()
    } catch (e: Exception) {
        onError("Fallo al eliminar la cuenta: ${e.localizedMessage}")
    }
}
