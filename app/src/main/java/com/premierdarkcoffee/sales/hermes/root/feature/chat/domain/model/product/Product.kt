package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity

data class Product(
    val id: String,
    val name: String,
    val label: String? = null,
    val owner: String? = null,
    val year: Int? = null,
    val model: String,
    val description: String,
    val category: Category,
    val price: Price,
    val stock: Int,
    val image: Image,
    val origin: String,
    val date: Long,
    val overview: List<Information>,
    val keywords: List<String>,
    val specifications: Specifications? = null,
    val warranty: Warranty? = null,
    val legal: String? = null,
    val warning: String? = null,
    val storeId: String? = null
) {

    fun toProductEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            name = name,
            label = label,
            owner = owner,
            year = year,
            model = model,
            description = description,
            category = category.toCategoryEntity(),
            price = price.toPriceEntity(),
            stock = stock,
            image = image.toImageEntity(),
            origin = origin,
            date = date,
            overview = overview.map { it.toInformationEntity() },
            keywords = keywords,
            specifications = specifications?.toSpecificationsEntity(),
            warranty = warranty?.toWarrantyEntity(),
            legal = legal,
            warning = warning,
            storeId = storeId
        )
    }
}
