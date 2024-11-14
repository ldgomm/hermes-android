package com.premierdarkcoffee.sales.hermes.root.util.view

//
//  MapView.kt
//  Hermes
//
//  Created by José Ruiz on 2/8/24.
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(onUseLocationButtonClicked: (LatLng) -> Unit) {
    var location by remember { mutableStateOf<LatLng?>(null) }
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(56.0, -3.0), 5f)
    }

    Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.fillMaxSize()) {
            GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState, onMapClick = { value ->
                location = value
            }, onMapLoaded = { isMapLoaded = true }) {
                location?.let { location ->
                    Marker(state = MarkerState(location), title = "User location")
                    onUseLocationButtonClicked(location)
                }
            }
            if (!isMapLoaded) {
                this@Column.AnimatedVisibility(visible = !isMapLoaded,
                                               modifier = Modifier.matchParentSize(),
                                               enter = EnterTransition.None,
                                               exit = fadeOut()) {
                    CircularProgressIndicator(modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize())
                }
            }
        }
    }
}
