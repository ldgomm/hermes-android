package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user

//
//  UserLocation.kt
//  Hermes
//
//  Created by José Ruiz on 7/8/24.
//

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.UUID

data class UserLocationEntity(@PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
                              @ColumnInfo(name = "name") val name: String = "",
                              @ColumnInfo(name = "latitude") val latitude: Double,
                              @ColumnInfo(name = "longitude") val longitude: Double)