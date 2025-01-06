package com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.model

//
//  PostClientRequest.kt
//  Hermes
//
//  Created by José Ruiz on 13/12/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.authentication.data.remote.dto.ClientDto
import kotlinx.serialization.Serializable

@Serializable
data class PostClientRequest(
    val key: String? = null,
    val client: ClientDto
)