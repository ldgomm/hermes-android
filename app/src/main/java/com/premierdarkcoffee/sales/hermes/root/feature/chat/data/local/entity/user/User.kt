package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.user

//
//  User.kt
//  Hermes
//
//  Created by José Ruiz on 7/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint

data class User(val name: String, val location: GeoPoint)
