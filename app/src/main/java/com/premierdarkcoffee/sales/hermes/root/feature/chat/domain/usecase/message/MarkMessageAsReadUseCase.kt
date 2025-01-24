package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.message

//
//  SendMessageUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 12/7/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.MessageRepositoriable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarkMessageAsReadUseCase @Inject constructor(private val repositoriable: MessageRepositoriable) {

    suspend operator fun invoke(message: MessageEntity, onMessageUpdated: () -> Unit) {
        withContext(Dispatchers.IO) {
            repositoriable.markMessageAsRead(message, onMessageUpdated)
        }
    }
}