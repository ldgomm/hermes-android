package com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.usecase

//
//  CreateClientUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 13/12/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.model.PostClientRequest
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.serviceable.AuthenticationServiceable
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.viewmodel.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateClientUseCase @Inject constructor(private val serviceable: AuthenticationServiceable) {

    operator fun invoke(
        url: String,
        request: PostClientRequest
    ): Flow<Result<LoginResponse>> {
        return serviceable.postClient(endpoint = url, request = request)
    }
}