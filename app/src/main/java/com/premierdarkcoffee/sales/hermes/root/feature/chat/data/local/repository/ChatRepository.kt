package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.repository

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.database.MainDatabase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.ChatRepositoriable
import javax.inject.Inject

class ChatRepository @Inject constructor(private val database: MainDatabase) : ChatRepositoriable {

    override suspend fun insertChatGPTMessage(chatMessage: ChatMessageEntity) {
        database.chatDao.insertChatMessage(chatMessage)
    }

    override suspend fun getChatGPTMessages(): List<ChatMessageEntity> {
        return database.chatDao.getAllChatMessages()
    }

    override suspend fun deleteChatGPTMessages() {
        database.chatDao.deleteAllChatMessages()
    }
}
