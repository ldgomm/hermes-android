package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message

//
//  MessageEntity.kt
//  Hermes
//
//  Created by José Ruiz on 12/7/24.
//

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.AttachmentDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.AttachmentTypeDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageStatusDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message.MessageTypeDto
import java.util.UUID

enum class MessageStatusEntity { SENT, DELIVERED, READ }

enum class MessageTypeEntity { TEXT, IMAGE, VIDEO, AUDIO, FILE }

data class AttachmentEntity(val id: String = UUID.randomUUID().toString(),
                            val url: String,
                            val type: AttachmentTypeEntity,
                            val size: Long,
                            val name: String) {

    fun toAttachmentDto(): AttachmentDto {
        return AttachmentDto(url = url, type = AttachmentTypeDto.valueOf(type.name), size = size, name = name)
    }
}

enum class AttachmentTypeEntity { IMAGE, VIDEO, AUDIO, FILE }

@Entity(tableName = "messages")
data class MessageEntity(@PrimaryKey(autoGenerate = false) val id: String,
                         val text: String,
                         val fromClient: Boolean,
                         val date: Long,
                         val clientId: String,
                         val storeId: String,
                         var status: MessageStatusEntity = MessageStatusEntity.SENT,
                         val type: MessageTypeEntity = MessageTypeEntity.TEXT,
                         val attachment: AttachmentEntity? = null,
                         val product: String? = null) {

    fun toMessageDto(): MessageDto {
        return MessageDto(id = id,
                          text = text,
                          fromClient = fromClient,
                          date = date,
                          clientId = clientId,
                          storeId = storeId,
                          status = MessageStatusDto.valueOf(status.name),
                          type = MessageTypeDto.valueOf(type.name),
                          attachment = attachment?.toAttachmentDto(),
                          product = product)
    }
}
