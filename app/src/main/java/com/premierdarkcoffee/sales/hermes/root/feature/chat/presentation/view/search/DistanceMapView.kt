package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search

//
//  DistanceMapView.kt
//  Hermes
//
//  Created by José Ruiz on 8/8/24.
//

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.premierdarkcoffee.sales.hermes.R
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import com.premierdarkcoffee.sales.hermes.root.util.function.getZoomLevel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DistanceMapView(
    userLocation: GeoPoint,
    distance: Int,
    onDistanceChange: (Int) -> Unit,
    onNavigateToUserViewButtonClicked: () -> Unit
) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(userLocation.coordinates[1], userLocation.coordinates[0]), 10f)
    }

    val formattedDistance = remember(distance) {
        NumberFormat.getNumberInstance(Locale.US).format(distance)
    }

    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.search_radius, distance),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )

        OutlinedButton(
            onClick = onNavigateToUserViewButtonClicked, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.change_my_location),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState, onMapLoaded = { isMapLoaded = true }) {
                val location = LatLng(userLocation.coordinates[1], userLocation.coordinates[0])
                Marker(state = MarkerState(location), title = stringResource(id = R.string.you_are_here))
                Circle(
                    center = location, radius = (distance * 1000).toDouble(), // Convert km to meters
                    strokeColor = Color.Blue, strokeWidth = 2f, fillColor = Color.Blue.copy(alpha = 0.1f)
                )
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

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val decrement = if (distance in 1..10) 1 else 10
                        onDistanceChange(distance - decrement)
                    }, enabled = distance > 1, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (distance in 1..10) "-1 km" else "-10 km")
                }

                Text(
                    text = "$formattedDistance km", style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold
                    ), modifier = Modifier.padding(8.dp)
                )

                Button(
                    onClick = {
                        val increment = if (distance in 1..10) 1 else 10
                        onDistanceChange(distance + increment)
                    }, enabled = distance < 500, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (distance in 1..10) "+1 km" else "+10 km")
                }
            }
        }
    }

    LaunchedEffect(key1 = userLocation, key2 = distance) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(userLocation.coordinates[1], userLocation.coordinates[0]), getZoomLevel(distance)
            ), 1000
        )
    }
}
