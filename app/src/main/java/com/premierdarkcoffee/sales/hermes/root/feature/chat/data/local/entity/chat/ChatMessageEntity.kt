package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.chat.ChatMessage
import java.util.UUID

@Entity(tableName = "chat")
data class ChatMessageEntity(@PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
                             val isUser: Boolean,
                             val date: Long = System.currentTimeMillis(),
                             val firstMessage: String,
                             val products: List<ProductEntity>? = null,
                             val secondMessage: String?,
                             val optionalProducts: List<ProductEntity>?) {

    fun toChatMessage(): ChatMessage {
        return ChatMessage(id = id,
                           isUser = isUser,
                           date = date,
                           firstMessage = firstMessage,
                           products = products?.map { it.toProduct() },
                           secondMessage = secondMessage,
                           optionalProducts = optionalProducts?.map { it.toProduct() })
    }
}