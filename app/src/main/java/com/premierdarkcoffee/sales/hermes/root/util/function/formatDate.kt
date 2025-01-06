package com.premierdarkcoffee.sales.hermes.root.util.function

//
//  formatDate.kt
//  Hermes
//
//  Created by José Ruiz on 13/7/24.
//

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(date: Long): String {
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(Date(date))
}
