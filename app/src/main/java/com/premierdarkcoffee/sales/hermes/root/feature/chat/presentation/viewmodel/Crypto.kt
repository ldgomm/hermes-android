package com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    return EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun getApiKey(context: Context): String {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    return sharedPreferences.getString("API_KEY", null) ?: ""
}

fun storeApiKey(context: Context, apiKey: String) {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    sharedPreferences.edit().putString("API_KEY", apiKey).apply()
}
