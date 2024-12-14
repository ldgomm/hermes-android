package com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.serviceable

//
//  AuthenticationServiceable.kt
//  Hermes
//
//  Created by José Ruiz on 13/12/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.model.PostClientRequest
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.viewmodel.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthenticationServiceable {

    fun postClient(
        endpoint: String,
        request: PostClientRequest
    ): Flow<Result<LoginResponse>>
}