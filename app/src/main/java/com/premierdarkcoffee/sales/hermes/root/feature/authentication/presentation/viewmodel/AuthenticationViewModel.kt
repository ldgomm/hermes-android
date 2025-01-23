package com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.data.remote.dto.ClientDto
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.model.PostClientRequest
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.usecase.CreateClientUseCase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.ImageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.GeoPointDto
import com.premierdarkcoffee.sales.hermes.root.util.function.getUrlFor
import com.premierdarkcoffee.sales.hermes.root.util.key.getClientKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(application: Application,
                                                  private val createClientUseCase: CreateClientUseCase) :
    AndroidViewModel(application) {
    private val clientKey: String = getClientKey(getApplication<Application>().applicationContext)

    // State variable to track the sign-in process status
    var state = mutableStateOf(false)
        private set

    /**
     * Updates the sign-in state.
     *
     * @param state A boolean representing whether the sign-in process is ongoing or not.
     */
    fun setSignInState(state: Boolean) {
        this.state.value = state
    }

    /**
     * Initiates anonymous sign-in  using Firebase Authentication.
     *
     * @param onSuccess A callback invoked upon successful sign-in.
     * @param onFailure A callback invoked when sign-in fails, with an exception provided.
     */
    fun signInWithFirebase(tokenId: String,
                           onSuccess: (userName: String, token: String) -> Unit,
                           onFailure: (exception: Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(tokenId, null)
                val instance = FirebaseAuth.getInstance()
                val task = instance.signInWithCredential(credential).await()
                task.user?.let { user ->
                    setSignInState(true)
                    handleClientCreation(user, onSuccess, onFailure)
                } ?: run {
                    onFailure(Exception("User ID is null"))
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    private fun handleClientCreation(user: FirebaseUser,
                                     onSuccess: (userName: String, token: String) -> Unit,
                                     onFailure: (exception: Throwable) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client: ClientDto = getFakeClient(user)
                Log.d(TAG, "AuthenticationViewModel | Client: $client")
                createClientUseCase(getUrlFor("cronos-client"),
                                    PostClientRequest(key = clientKey, client = client)).collect { result ->
                    result.onSuccess { response ->
                        Log.d(TAG, "AuthenticationViewModel | signInWithFirebase: client created")
                        withContext(Dispatchers.Main) {
                            onSuccess(user.displayName ?: "No username", response.token)
                        }
                    }.onFailure { exception ->
                        Log.e(TAG, "AuthenticationViewModel | signInWithFirebase: client wasn't created ${exception.message}")
                        withContext(Dispatchers.Main) {
                            onFailure(exception)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "AuthenticationViewModel | Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    onFailure(e)
                }
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
    private fun handleUserCreation(userId: String, onSuccess: (String) -> Unit, onFailure: (exception: Throwable) -> Unit) {
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

fun getFakeClient(user: FirebaseUser): ClientDto {
    return ClientDto(id = user.uid,
                     name = user.displayName ?: "No name",
                     email = user.email ?: "",
                     phone = user.phoneNumber ?: "",
                     image = ImageDto("", "", false),
                     location = GeoPointDto(type = "Point", coordinates = listOf(-78.484498, -0.182847)),
                     createdAt = System.currentTimeMillis())
}

@Serializable
data class LoginResponse(val token: String)