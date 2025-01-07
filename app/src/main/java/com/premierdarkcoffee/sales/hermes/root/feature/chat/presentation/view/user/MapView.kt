package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.user

//
//  MapView.kt
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint

@Composable
fun MapView(
    value: GeoPoint,
    location: LatLng = LatLng(value.coordinates[1], value.coordinates[0]),
    onUseLocationButtonClicked: (LatLng) -> Unit
) {
    var currentLocation by remember { mutableStateOf(location) }
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Google Map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { newLocation ->
                    currentLocation = newLocation
                    onUseLocationButtonClicked(currentLocation)
                },
                onMapLoaded = { isMapLoaded = true }
            ) {
                Marker(
                    state = MarkerState(currentLocation),
                    title = stringResource(R.string.you_are_here),
                    snippet = stringResource(R.string.current_location_marker)
                )
            }

            // Loading Indicator
            if (!isMapLoaded) {
                this@Column.AnimatedVisibility(
                    visible = !isMapLoaded,
                    modifier = Modifier.matchParentSize(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                            .wrapContentSize(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Composable
fun SimpleMapView(
    location: LatLng,
    modifier: Modifier = Modifier
) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Google Map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = { isMapLoaded = true }
            ) {
                Marker(
                    state = MarkerState(location),
                    title = stringResource(R.string.you_are_here),
                    snippet = stringResource(R.string.current_location_marker)
                )
            }

            // Loading Indicator
            if (!isMapLoaded) {
                this@Column.AnimatedVisibility(
                    visible = !isMapLoaded,
                    modifier = Modifier.matchParentSize(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                            .wrapContentSize(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
