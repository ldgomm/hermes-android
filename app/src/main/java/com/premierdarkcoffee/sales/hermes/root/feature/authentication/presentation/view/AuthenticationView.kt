package com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.hermes.R.drawable.lock_person
import com.premierdarkcoffee.sales.hermes.R.string.be_yourself_every_purchase
import com.premierdarkcoffee.sales.hermes.R.string.default_private
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.titleStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationView(
    state: Boolean,
    onSignInAnonymouslyButtonClicked: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .nestedScroll(scrollBehavior.nestedScrollConnection)
        .statusBarsPadding()
        .navigationBarsPadding(), topBar = {
        TopAppBar(title = {
            Text("Hermes", style = titleStyle, maxLines = 1, overflow = TextOverflow.Clip)
        }, scrollBehavior = scrollBehavior)
    }) {
        Column(
            Modifier.padding(it), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(Modifier.weight(1f)) {}
            Column(
                modifier = Modifier
                    .weight(9f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(120.dp),
                    painter = painterResource(id = lock_person),
                    contentDescription = "Google Logo",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Text(text = stringResource(id = default_private), fontWeight = FontWeight.Bold)
                Text(
                    text = stringResource(id = be_yourself_every_purchase),

                    modifier = Modifier.padding(bottom = 40.dp, top = 4.dp), textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(30.dp))

                SignInWithGoogleButtonView(
                    signedInState = state, onSignInAnonymouslyButtonClicked = onSignInAnonymouslyButtonClicked
                )
            }
        }
    }
}
