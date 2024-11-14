package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.user

//
//  EditUserView.kt
//  Hermes
//
//  Created by José Ruiz on 16/8/24.
//

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.chat.titleStyle
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserView(
    user: User,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current

    var showAlert by remember { mutableStateOf(false) }
    var newLocation by remember { mutableStateOf<LatLng?>(null) }
    var oldLocation by remember { mutableStateOf<LatLng?>(null) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(user.name, style = titleStyle) }, navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(ImageVector.vectorResource(R.drawable.arrow_back), null)
            }
        }, actions = { Button({ showAlert = true }) { Text("Guardar") } })
    }) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .clip(RoundedCornerShape(8.dp))
        ) {
            MapView(user.location) { selectedLocation ->
                oldLocation = LatLng(user.location.coordinates[1], user.location.coordinates[0])
                newLocation = selectedLocation
            }
        }

        if (showAlert) {
            AlertDialog(onDismissRequest = { showAlert = false }, title = { Text("Cambiar el lugar de buúsqueda") }, text = {
                Text("Esta seguro que desea cambiar su ubicación?")
            }, confirmButton = {
                Button(onClick = {
                    SharedPreferencesHelper.setLatitude(context, newLocation!!.latitude)
                    SharedPreferencesHelper.setLongitude(context, newLocation!!.longitude)
                    showAlert = false
                    popBackStack()
                }) {
                    Text("Sí")
                }
            }, dismissButton = {
                Button(onClick = {
                    newLocation = oldLocation
                    showAlert = false
                }) {
                    Text("No")
                }
            })
        }
    }
}
