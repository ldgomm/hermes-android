package com.premierdarkcoffee.sales.hermes.root.util.helper

//
//  SecurePreferencesHelper.kt
//  Maia
//
//  Created by José Ruiz on 8/8/24.
//

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecurePreferencesHelper {
    private const val PREFERENCE_NAME = "secure_user_preferences"
    private const val TOKEN_KEY = "jwt"

    private fun getSecurePreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            context,
            PREFERENCE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getToken(context: Context): String? {
        return getSecurePreferences(context).getString(TOKEN_KEY, "")
    }

    fun setToken(
        context: Context,
        token: String
    ) {
        getSecurePreferences(context).edit().putString(TOKEN_KEY, token).apply()
    }

    fun deleteToken(context: Context) {
        getSecurePreferences(context).edit().remove(TOKEN_KEY).apply()
    }
}
