package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.chat

//
//  DeleteChatMessagesUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 8/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.ChatRepositoriable
import javax.inject.Inject

class DeleteChatMessagesUseCase @Inject constructor(private val chatRepository: ChatRepositoriable) {

    suspend operator fun invoke() {
        chatRepository.deleteChatGPTMessages()
    }
}
