package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message

interface MessageRepositoriable {

    suspend fun addMessage(message: MessageDto)

    suspend fun fetchMessages(callback: (List<Message>) -> Unit)

//    suspend fun fetchLocalMessages(callback: (List<Message>) -> Unit)

    suspend fun markMessageAsRead(
        message: MessageEntity,
        onMessageUpdated: () -> Unit
    )
}
