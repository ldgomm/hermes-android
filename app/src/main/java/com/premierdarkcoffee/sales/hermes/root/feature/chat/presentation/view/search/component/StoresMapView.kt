package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

//
//  StoresMapView.kt
//  Hermes
//
//  Created by José Ruiz on 16/8/24.
//

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import com.premierdarkcoffee.sales.hermes.root.util.function.getZoomLevel
import com.premierdarkcoffee.sales.hermes.root.util.helper.SharedPreferencesHelper

@Composable
fun StoresMapView(
    user: User,
    stores: List<Store>,
    onNavigateToStoreMarkerClicked: (String) -> Unit
) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(user.location.coordinates[1], user.location.coordinates[0]), 10f)
    }

    val context = LocalContext.current
    val distance = SharedPreferencesHelper.getDistance(context)

    Column(modifier = Modifier, verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = { isMapLoaded = true }) {
                val userLocation = LatLng(user.location.coordinates[1], user.location.coordinates[0])
                Marker(
                    state = MarkerState(userLocation),
                    title = stringResource(id = R.string.you_are_here),
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
                stores.forEach { store ->
                    val storeLocation = LatLng(store.address.location.coordinates[1], store.address.location.coordinates[0])
                    Marker(state = MarkerState(storeLocation),
                           title = store.name,
                           icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                           onInfoWindowClick = {
                               onNavigateToStoreMarkerClicked(Gson().toJson(store))
                           })
                }
            }
            if (!isMapLoaded) {
                this@Column.AnimatedVisibility(
                    visible = !isMapLoaded, modifier = Modifier.matchParentSize(), enter = EnterTransition.None, exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .wrapContentSize()
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = user.location, key2 = distance) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(user.location.coordinates[1], user.location.coordinates[0]), getZoomLevel(distance)
            ), 1000
        )
    }
}
