package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.user

//
//  UserView.kt
//  Hermes
//
//  Created by José Ruiz on 26/8/24.
//

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserView(user: User, popBackStack: () -> Unit, onEditUserButtonClicked: () -> Unit, onSettingsButtonClicked: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = user.name,
                 style = MaterialTheme.typography.titleLarge,
                 modifier = Modifier.semantics { contentDescription = user.name })
        }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                     contentDescription = stringResource(R.string.back))
            }
        }, actions = {
            IconButton(onEditUserButtonClicked) {
                Icon(imageVector = Icons.Default.Edit, null)
            }
            IconButton(onSettingsButtonClicked) {
                Icon(imageVector = Icons.Default.Settings, null)
            }
        })
    }) { paddingValues ->
        Column(verticalArrangement = Arrangement.spacedBy(20.dp), modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()) {

            // Location Section
            val mapLocationDescription = stringResource(R.string.map_location_description)
            Section {
                val location = LatLng(user.location.coordinates[1], user.location.coordinates[0])
                UserMapView(location = location,
                            modifier = Modifier
                                .height(300.dp)
                                .clip(RoundedCornerShape(11.dp))
                                .padding(8.dp)
                                .semantics {
                                    contentDescription = (mapLocationDescription)
                                })
            }

            // Privacy Information
            Text(text = stringResource(id = R.string.privacy_statement),
                 style = MaterialTheme.typography.bodyMedium,
                 color = MaterialTheme.colorScheme.onSurfaceVariant,
                 textAlign = TextAlign.Center,
                 modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun Section(content: @Composable () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(16.dp)) {
        content()
    }
}
