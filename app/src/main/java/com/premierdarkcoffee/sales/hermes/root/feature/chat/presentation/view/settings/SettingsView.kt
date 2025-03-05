package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.settings

//
//  SettingsView.kt
//  Hermes
//
//  Created by José Ruiz on 1/4/25.
//

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.premierdarkcoffee.sales.hermes.root.util.consttant.Constant.clientId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(onUserSignedOutActionTriggered: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Google Sign-In Client
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context,
                               GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                   .requestIdToken(clientId) // Web client ID from Firebase
                                   .requestEmail().build())
    }

    // Launcher para flujo de re-autenticación
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            val user = FirebaseAuth.getInstance().currentUser

            if (idToken != null && user != null) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        user.reauthenticate(credential).await()
                        user.delete().await()
                        onUserSignedOutActionTriggered()
                    } catch (e: Exception) {
                        errorMessage = "Error al eliminar la cuenta: ${e.localizedMessage}"
                    }
                }
            } else {
                errorMessage = "No se pudo obtener el token de Google."
            }
        } catch (e: ApiException) {
            errorMessage = "Error en el login de Google: ${e.localizedMessage}"
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Configuración", style = MaterialTheme.typography.titleLarge) })
    }) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)) {
            Button(onClick = { showDeleteDialog = true },
                   colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Eliminar cuenta", textAlign = TextAlign.Center)
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(onDismissRequest = { showDeleteDialog = false },
                    title = { Text("¿Estás seguro?") },
                    text = { Text("Eliminar tu cuenta es irreversible. Todos tus datos se perderán.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDeleteDialog = false
                            val user = FirebaseAuth.getInstance().currentUser
                            if (user != null) {
                                coroutineScope.launch {
                                    deleteUserAccount(user = user,
                                                      onSuccess = { onUserSignedOutActionTriggered() },
                                                      onError = { msg -> errorMessage = msg },
                                                      onReauthNeeded = {
                                                          val signInIntent = googleSignInClient.signInIntent
                                                          launcher.launch(signInIntent)
                                                      })
                                }
                            }
                        }) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancelar")
                        }
                    })
    }

    if (errorMessage != null) {
        AlertDialog(onDismissRequest = { errorMessage = null },
                    title = { Text("Error") },
                    text = { Text(errorMessage ?: "") },
                    confirmButton = {
                        TextButton(onClick = { errorMessage = null }) {
                            Text("Aceptar")
                        }
                    })
    }
}
