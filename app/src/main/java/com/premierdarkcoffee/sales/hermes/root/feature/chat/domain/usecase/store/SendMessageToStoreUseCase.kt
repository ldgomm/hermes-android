package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store

//
//  SendMessageUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 12/7/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.MessageRepositoriable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SendMessageToStoreUseCase @Inject constructor(private val repositoriable: MessageRepositoriable) {

    suspend operator fun invoke(message: MessageDto) {
        withContext(Dispatchers.IO) {
            repositoriable.addMessage(message)
        }
    }
}