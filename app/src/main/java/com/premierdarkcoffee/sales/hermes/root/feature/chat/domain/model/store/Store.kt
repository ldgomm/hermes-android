package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.StoreDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Image

data class Store(
    val id: String,
    val name: String,
    val image: Image,
    val address: Address,
    val phoneNumber: String,
    val emailAddress: String,
    val website: String,
    val description: String,
    val returnPolicy: String,
    val refundPolicy: String,
    val brands: List<String>,
    val createdAt: Long,
    val status: StoreStatus
) {

    fun toStoreDto(): StoreDto {
        return StoreDto(
            id = id,
            name = name,
            image = image.toImageDto(),
            address = address.toAddressDto(),
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            website = website,
            description = description,
            returnPolicy = returnPolicy,
            refundPolicy = refundPolicy,
            brands = brands,
            createdAt = createdAt,
            status = status.toStoreStatusDto()
        )
    }

    fun toStoreEntity(): StoreEntity {
        return StoreEntity(
            id = id,
            name = name,
            image = image.toImageEntity(),
            address = address.toAddressEntity(),
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            website = website,
            description = description,
            returnPolicy = returnPolicy,
            refundPolicy = refundPolicy,
            brands = brands,
            createdAt = createdAt,
            status = status.toStoreStatusEntity()
        )
    }
}
