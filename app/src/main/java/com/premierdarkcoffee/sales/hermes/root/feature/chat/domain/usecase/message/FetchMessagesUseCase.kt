package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.message

//
//  SendMessageUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 12/7/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Message
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.MessageRepositoriable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchMessagesUseCase @Inject constructor(private val repositoriable: MessageRepositoriable) {

    suspend operator fun invoke(callback: (List<Message>) -> Unit) {
        withContext(Dispatchers.IO) {
            repositoriable.fetchMessages(callback)
        }
    }
}
