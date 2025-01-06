package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity

class ChatMessageConverter {

    @TypeConverter
    fun fromChatMessagesList(list: List<ChatMessageEntity>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toChatMessagesList(value: String?): List<ChatMessageEntity>? {
        return value?.let { Gson().fromJson(it, object : TypeToken<List<ChatMessageEntity>>() {}.type) }
    }

}