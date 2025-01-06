package com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.view

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.util.consttant.Constant.clientId
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@Composable
fun AuthenticationView(
    oneTapState: OneTapSignInState,
    signedInState: Boolean,
    onSignInWithGoogleButtonClicked: (Boolean) -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(Color(0xFF222222))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF222222))
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo con sombra
            Image(
                painter = painterResource(id = R.drawable.logo), // Reemplaza con tu logo
                contentDescription = "Hermes Logo",
                modifier = Modifier
                    .size(200.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Título principal
            Text(
                text = "Hermes",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Subtítulo
            Text(
                text = "Encuentra grandes ofertas al alcance de tu mano.",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp, bottom = 40.dp)
            )

            Spacer(Modifier.height(200.dp))
            SignInWithGoogleButtonView(
                signedInState = signedInState, onSignInWithGoogleButtonClicked = onSignInWithGoogleButtonClicked
            )
        }
    }

    OneTapSignInWithGoogle(state = oneTapState, clientId = clientId, onTokenIdReceived = { tokenId ->
        Log.d(TAG, "AuthenticationView: token = $tokenId")
        onTokenIdReceived(tokenId)
    }, onDialogDismissed = onDialogDismissed)
}
