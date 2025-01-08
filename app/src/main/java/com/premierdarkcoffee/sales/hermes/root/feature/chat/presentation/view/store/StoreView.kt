package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.store

//
//  StoreView.kt
//  Hermes
//
//  Created by José Ruiz on 16/8/24.
//

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store

@Composable
fun StoreView(store: Store?) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(store?.name ?: "Error")
        }
    }
}

