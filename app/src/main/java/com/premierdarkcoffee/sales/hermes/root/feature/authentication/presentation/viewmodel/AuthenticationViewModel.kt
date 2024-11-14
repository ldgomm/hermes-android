package com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor() : ViewModel() {

    // State variable to track the sign-in process status
    var state = mutableStateOf(false)
        private set

    /**
     * Updates the sign-in state.
     *
     * @param state A boolean representing whether the sign-in process is ongoing or not.
     */
    private fun setSignInState(state: Boolean) {
        this.state.value = state
    }

    /**
     * Initiates anonymous sign-in  using Firebase Authentication.
     *
     * @param onSuccess A callback invoked upon successful sign-in.
     * @param onFailure A callback invoked when sign-in fails, with an exception provided.
     */
    fun signInAnonymously(
        onSuccess: (String) -> Unit,
        onFailure: (exception: Throwable) -> Unit
    ) {
        setSignInState(true)
        viewModelScope.launch {
            delay(3000)  // Simulate a delay to represent processing time
            try {
                val instance = FirebaseAuth.getInstance()
                instance.signInAnonymously().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch(Dispatchers.Main) {
                            val userId = instance.currentUser?.uid
                            if (userId != null) {
                                handleUserCreation(userId, onSuccess, onFailure)
                            } else {
                                onFailure(Exception("User ID is null"))
                            }
                        }
                    } else {
                        setSignInState(false)
                        onFailure(Exception("Sign in failed"))
                    }
                }.addOnFailureListener { exception ->
                    setSignInState(false)
                    onFailure(exception)
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    /**
     * Handles the creation of a new user after a successful anonymous sign-in.
     *
     * @param userId The unique ID of the signed-in user.
     * @param onSuccess A callback invoked upon successful user creation.
     * @param onFailure A callback invoked when user creation fails, with an exception provided.
     */
    private fun handleUserCreation(
        userId: String,
        onSuccess: (String) -> Unit,
        onFailure: (exception: Throwable) -> Unit
    ) {
        viewModelScope.launch {
            val userName = userId.substring(startIndex = 0, endIndex = 5).lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            try {
                withContext(Dispatchers.Main) {
                    onSuccess(userName)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure(e)
                }
            }
        }
    }
}
