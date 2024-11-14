package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable

//
//  CharRepositoriable.kt
//  Hermes
//
//  Created by José Ruiz on 8/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity

interface ChatRepositoriable {

    suspend fun insertChatGPTMessage(chatMessage: ChatMessageEntity)

    suspend fun getChatGPTMessages(): List<ChatMessageEntity>

    suspend fun deleteChatGPTMessages()
}

