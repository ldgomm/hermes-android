package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter

//
//  MessageConverter.kt
//  Hermes
//
//  Created by José Ruiz on 12/7/24.
//

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.AttachmentEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageStatusEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageTypeEntity

class MessageConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromMessageStatus(value: MessageStatusEntity): String {
        return value.name
    }

    @TypeConverter
    fun toMessageStatus(value: String): MessageStatusEntity {
        return MessageStatusEntity.valueOf(value)
    }

    @TypeConverter
    fun fromMessageType(value: MessageTypeEntity): String {
        return value.name
    }

    @TypeConverter
    fun toMessageType(value: String): MessageTypeEntity {
        return MessageTypeEntity.valueOf(value)
    }

    @TypeConverter
    fun fromAttachment(attachment: AttachmentEntity?): String? {
        return if (attachment == null) null else gson.toJson(attachment)
    }

    @TypeConverter
    fun toAttachment(attachmentString: String?): AttachmentEntity? {
        return if (attachmentString == null) null else gson.fromJson(attachmentString,
                                                                     object : TypeToken<AttachmentEntity>() {}.type)
    }
}
