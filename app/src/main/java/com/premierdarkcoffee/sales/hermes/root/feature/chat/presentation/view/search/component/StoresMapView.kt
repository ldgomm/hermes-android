package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.component

//
//  StoresMapView.kt
//  Hermes
//
//  Created by José Ruiz on 16/8/24.
//

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.Gson
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user.User
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store


@Composable
fun StoresMapView(user: User, stores: List<Store>, onNavigateToStoreMarkerClicked: (String) -> Unit) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(user.location.coordinates[1], user.location.coordinates[0]), 10f)
    }

    val allCoordinates = remember(stores, user.location) {
        stores.map { store ->
            LatLng(store.address.location.coordinates[1], store.address.location.coordinates[0])
        }.toMutableList().also {
            it.add(LatLng(user.location.coordinates[1], user.location.coordinates[0]))  // Add user last
        }
    }

    // Main Container
    Column(modifier = Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.Bottom,
           horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Google Map
            GoogleMap(modifier = Modifier.fillMaxSize(),
                      cameraPositionState = cameraPositionState,
                      onMapLoaded = { isMapLoaded = true }) {
                // User Location Marker
                val userLocation = LatLng(user.location.coordinates[1], user.location.coordinates[0])
                val customUserIcon = bitmapDescriptorFromVector(R.drawable.person_outline)
                Marker(state = MarkerState(userLocation),
                       title = stringResource(id = R.string.you_are_here),
                       icon = customUserIcon,
                       snippet = stringResource(id = R.string.your_current_location),
                       onInfoWindowClick = {})

                // Store Markers
                stores.forEach { store ->
                    val storeLocation = LatLng(store.address.location.coordinates[1], store.address.location.coordinates[0])
                    val customStoreIcon = bitmapDescriptorFromVector(R.drawable.storefront) // your vector asset

                    Marker(state = MarkerState(storeLocation),
                           title = store.name,
                           icon = customStoreIcon,
                           snippet = stringResource(id = R.string.store_location),
                           onInfoWindowClick = {
                               onNavigateToStoreMarkerClicked(Gson().toJson(store))
                           })
                }
            }

            // Loading Indicator
            if (!isMapLoaded) {
                this@Column.AnimatedVisibility(visible = !isMapLoaded,
                                               modifier = Modifier.matchParentSize(),
                                               enter = fadeIn(),
                                               exit = fadeOut()) {
                    CircularProgressIndicator(modifier = Modifier
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                        .wrapContentSize(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }

    LaunchedEffect(key1 = user.location, key2 = stores) {
        if (allCoordinates.isNotEmpty()) {
            val latitudes = allCoordinates.map { it.latitude }
            val longitudes = allCoordinates.map { it.longitude }

            // Fallback in case there's only one coordinate
            val minLat = latitudes.minOrNull() ?: user.location.coordinates[1]
            val maxLat = latitudes.maxOrNull() ?: user.location.coordinates[1]
            val minLon = longitudes.minOrNull() ?: user.location.coordinates[0]
            val maxLon = longitudes.maxOrNull() ?: user.location.coordinates[0]

            // 3) Find the center of that bounding box
            val centerLat = (minLat + maxLat) / 2.0
            val centerLon = (minLon + maxLon) / 2.0

            // 4) Multiply span by 1.5 for extra padding (same as Swift code)
            val latDelta = (maxLat - minLat) * 1.5
            val lonDelta = (maxLon - minLon) * 1.5

            // 5) Convert that “span” into a LatLngBounds
            //    By shifting the center ± half the delta in each direction
            val southwest = LatLng(centerLat - latDelta / 2, centerLon - lonDelta / 2)
            val northeast = LatLng(centerLat + latDelta / 2, centerLon + lonDelta / 2)
            val bounds = LatLngBounds(southwest, northeast)

            // 6) Animate camera to show all points within that expanded bounding box
            cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 0), 1000)
        }
    }

}
