package com.premierdarkcoffee.sales.hermes.root.feature.authentication.data.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.model.PostClientRequest
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.serviceable.AuthenticationServiceable
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.presentation.viewmodel.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthenticationService @Inject constructor(private val httpClient: HttpClient) : AuthenticationServiceable {

    override fun postClient(endpoint: String, request: PostClientRequest): Flow<Result<LoginResponse>> {
        Log.d(TAG, "AuthenticationService | postStore: $endpoint")
        val jsonRequest = Json.encodeToString(request)
        Log.d(TAG, "AuthenticationService | postStore JSON: $jsonRequest")
        return flow {
            try {
                val response: HttpResponse = httpClient.post(endpoint) {
                    contentType(ContentType.Application.Json)
                    setBody(jsonRequest)
                }

                when (response.status) {
                    HttpStatusCode.Created -> {
                        val responseBody = response.body<LoginResponse>()
                        Log.d(TAG, "AuthenticationService | Store created: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    HttpStatusCode.Found, HttpStatusCode.OK -> {
                        val responseBody = response.body<LoginResponse>()
                        Log.d(TAG, "AuthenticationService | Store already exists: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    else -> {
                        Log.d(TAG, "AuthenticationService | Server error with status code: ${response.status}")
                        emit(Result.failure(RuntimeException("Server responded with error status: ${response.status}")))
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "AuthenticationService | Store wasn't created: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }
}
