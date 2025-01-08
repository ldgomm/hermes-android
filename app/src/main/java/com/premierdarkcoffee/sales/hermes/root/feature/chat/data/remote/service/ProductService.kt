package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.service

import android.content.ContentValues.TAG
import android.util.Log
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.request.ClientProductRequest
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.response.ClientProductResponse
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.ProductServiceable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.TooManyRequests
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductService @Inject constructor(private val httpClient: HttpClient) : ProductServiceable {

    override fun searchProducts(
        url: String,
        request: ClientProductRequest
    ): Flow<Result<ClientProductResponse>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }

                when (response.status) {
                    OK -> {
                        val responseBody = response.body<ClientProductResponse>()
                        Log.d(TAG, "ProductService | Product added: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    BadRequest, Unauthorized, Forbidden, NotFound, InternalServerError -> {
                        Log.d(TAG, "ProductService | Error with status code: ${response.status}")
                        emit(Result.failure(RuntimeException("Server responded with status code ${response.status}")))
                    }

                    TooManyRequests -> {
                        Log.d(TAG, "ProductService | Error with status code: ${response.status}")
                        emit(Result.failure(RuntimeException("We are sorry but you've reached the limit for requests, please, try again tomorrow.")))
                    }

                    else -> {
                        Log.d(
                            TAG, "ProductService | Unexpected response status: ${response.status}"
                        )
                        emit(Result.failure(Exception("Unexpected response status: ${response.status}")))
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "ProductService | Failed to post product: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }
}
