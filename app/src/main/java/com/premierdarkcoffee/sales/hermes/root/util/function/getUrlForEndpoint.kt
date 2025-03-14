package com.premierdarkcoffee.sales.hermes.root.util.function

//
//  getUrlFor.kt
//  Hermes
//
//  Created by José Ruiz on 5/8/24.
//

import android.content.ContentValues.TAG
import android.util.Log
import java.net.URLEncoder

fun getUrlForEndpoint(endpoint: String, keywords: String? = null, storeId: String? = null): String {
    val baseUrl = "https://www.sales.premierdarkcoffee.com/$endpoint"
    val encodedKeywords = keywords?.let { URLEncoder.encode(it, "UTF-8") }
    val encodedStoreId = storeId?.let { URLEncoder.encode(it, "UTF-8") }

    val queryParams = mutableListOf<String>()

    if (encodedKeywords != null) {
        queryParams.add("keywords=$encodedKeywords")
    }
    if (storeId != null) {
        queryParams.add("storeId=$encodedStoreId")
    }

    val urlString = if (queryParams.isNotEmpty()) {
        "$baseUrl?${queryParams.joinToString("&")}"
    } else {
        baseUrl
    }
    Log.d(TAG, "getUrlFor: $urlString")
    return urlString
}
