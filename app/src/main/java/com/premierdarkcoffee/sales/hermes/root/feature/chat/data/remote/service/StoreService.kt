package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.service

//
//  StoreService.kt
//  Maia
//
//  Created by José Ruiz on 31/7/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.StoreDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.StoreServiceable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreService @Inject constructor(private val httpClient: HttpClient) : StoreServiceable {

    override fun getStoreById(endpoint: String): Flow<Result<StoreDto>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.get(endpoint)
                emit(Result.success(response.body()))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}
