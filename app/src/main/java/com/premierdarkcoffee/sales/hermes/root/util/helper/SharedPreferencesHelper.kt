package com.premierdarkcoffee.sales.hermes.root.util.helper

//
//  DistanceHelper.kt
//  Hermes
//
//  Created by José Ruiz on 8/8/24.
//

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {
    private const val PREFERENCE_NAME = "user_preferences"
    private const val DISTANCE_KEY = "distance_key"
    private const val NAME_KEY = "name_key"
    private const val LATITUDE_KEY = "latitude_key"
    private const val LONGITUDE_KEY = "longitude_key"
    private const val DO_NOT_AGAIN_DISTANCE_ALERT = "do_not_show_again_distance_alert"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getDistance(context: Context): Int {
        return getPreferences(context).getInt(DISTANCE_KEY, 50) // Default value is 50
    }

    fun setDistance(context: Context, distance: Int) {
        getPreferences(context).edit().putInt(DISTANCE_KEY, distance).apply()
    }

    fun getName(context: Context): String? {
        return getPreferences(context).getString(NAME_KEY, "Sin nombre")
    }

    fun setName(context: Context, name: String) {
        getPreferences(context).edit().putString(NAME_KEY, name).apply()
    }

    fun getLatitude(context: Context): Double {
        return getPreferences(context).getFloat(LATITUDE_KEY, 0.0f).toDouble()
    }

    fun setLatitude(context: Context, latitude: Double) {
        getPreferences(context).edit().putFloat(LATITUDE_KEY, latitude.toFloat()).apply()
    }

    fun getLongitude(context: Context): Double {
        return getPreferences(context).getFloat(LONGITUDE_KEY, 0.0f).toDouble()
    }

    fun setLongitude(context: Context, longitude: Double) {
        getPreferences(context).edit().putFloat(LONGITUDE_KEY, longitude.toFloat()).apply()
    }

    fun getDoNotShowAgainDistanceAlert(context: Context): Boolean {
        return getPreferences(context).getBoolean(DO_NOT_AGAIN_DISTANCE_ALERT, false)
    }

    fun setDoNotShowAgainDistanceAlert(context: Context, value: Boolean) {
        getPreferences(context).edit().putBoolean(DO_NOT_AGAIN_DISTANCE_ALERT, value).apply()
    }
}
