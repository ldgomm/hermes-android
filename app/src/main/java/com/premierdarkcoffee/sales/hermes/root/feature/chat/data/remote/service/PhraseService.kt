package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.service

import android.content.ContentValues
import android.util.Log
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.PhraseServiceable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.view.search.SearchPhraseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhraseService @Inject constructor(private val httpClient: HttpClient) : PhraseServiceable {

    override fun getPhrases(url: String): Flow<Result<List<SearchPhraseDto>>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.get(url)
                Log.d(ContentValues.TAG, "ProductService | We have phrases: ${response.body<List<SearchPhraseDto>>()}")
                emit(Result.success(response.body()))
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "ProductService | We have no phrases: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }
}