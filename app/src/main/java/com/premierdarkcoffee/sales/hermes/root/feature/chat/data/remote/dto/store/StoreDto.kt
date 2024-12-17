package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.ImageDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.Store
import kotlinx.serialization.Serializable

@Serializable
data class StoreDto(
    val id: String,
    val name: String,
    val image: ImageDto,
    val address: AddressDto,
    val phoneNumber: String,
    val emailAddress: String,
    val website: String,
    val description: String,
    val returnPolicy: String,
    val refundPolicy: String,
    val brands: List<String>,
    val createdAt: Long,
    val status: StoreStatusDto
) {

    fun toStore(): Store {
        return Store(
            id = id,
            name = name,
            image = image.toImage(),
            address = address.toAddress(),
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            website = website,
            description = description,
            returnPolicy = returnPolicy,
            refundPolicy = refundPolicy,
            brands = brands,
            createdAt = createdAt,
            status = status.toStoreStatus()
        )
    }
}
