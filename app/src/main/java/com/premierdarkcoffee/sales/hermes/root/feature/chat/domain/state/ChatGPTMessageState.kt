package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.state

//
//  ChatMessageState.kt
//  Hermes
//
//  Created by José Ruiz on 8/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity

data class ChatGPTMessageState(val messages: List<ChatMessageEntity>? = null)
