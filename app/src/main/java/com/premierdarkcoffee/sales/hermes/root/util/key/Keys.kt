package com.premierdarkcoffee.sales.hermes.root.util.key

import android.content.Context
import com.premierdarkcoffee.sales.hermes.root.feature.chat.presentation.viewmodel.getApiKey

fun getClientKey(context: Context): String {
    return getApiKey(context)
}