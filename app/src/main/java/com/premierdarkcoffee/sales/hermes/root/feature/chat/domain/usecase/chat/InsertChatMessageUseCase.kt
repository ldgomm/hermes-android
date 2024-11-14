package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.chat

//
//  AddChatMessageUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 8/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.ChatRepositoriable
import javax.inject.Inject

class InsertChatMessageUseCase @Inject constructor(private val chatRepository: ChatRepositoriable) {

    suspend operator fun invoke(chatMessage: ChatMessageEntity) {
        chatRepository.insertChatGPTMessage(chatMessage)
    }
}
