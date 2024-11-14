package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.message

//
//  AttachmentDto.kt
//  Hermes
//
//  Created by José Ruiz on 7/11/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.AttachmentEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.AttachmentTypeEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.Attachment
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.message.AttachmentType
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AttachmentDto(
    val id: String = UUID.randomUUID().toString(),
    val url: String = "",
    val type: AttachmentTypeDto = AttachmentTypeDto.IMAGE,
    val size: Long = 0L,
    val name: String = ""
) {

    fun toAttachment(): Attachment {
        return Attachment(url = url, type = AttachmentType.valueOf(type.name), size = size, name = name)
    }

    fun toAttachmentEntity(): AttachmentEntity {
        return AttachmentEntity(url = url, type = AttachmentTypeEntity.valueOf(type.name), size = size, name = name)
    }
}