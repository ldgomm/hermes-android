package com.premierdarkcoffee.sales.hermes

//
//  NoInternetView.kt
//  Hermes
//
//  Created by José Ruiz on 7/8/24.
//

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NoInternetView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No Internet Connection", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
    }
}

@Composable
fun UnstableConnectionView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Unstable Internet Connection", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
    }
}
