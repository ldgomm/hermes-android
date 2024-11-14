package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import java.util.UUID

data class ChatMessage(val id: String = UUID.randomUUID().toString(),
                       val isUser: Boolean,
                       val date: Long = System.currentTimeMillis(),
                       val firstMessage: String,
                       val products: List<Product>? = null,
                       val secondMessage: String?,
                       val optionalProducts: List<Product>?) {

    fun toChatMessageEntity(): ChatMessageEntity {
        return ChatMessageEntity(id = id,
                                 isUser = isUser,
                                 date = date,
                                 firstMessage = firstMessage,
                                 products = products?.map { it.toProductEntity() },
                                 secondMessage = secondMessage,
                                 optionalProducts = optionalProducts?.map { it.toProductEntity() })
    }
}
