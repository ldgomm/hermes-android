package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(val id: String,
                      val name: String,
                      val label: String? = null,
                      val owner: String? = null,
                      val year: Int? = null,
                      val model: String,
                      val description: String,
                      val category: CategoryDto,
                      val price: PriceDto,
                      val stock: Int,
                      val image: ImageDto,
                      val origin: String,
                      val date: Long,
                      val overview: List<InformationDto>,
                      val keywords: List<String>,
                      val specifications: SpecificationsDto? = null,
                      val warranty: WarrantyDto? = null,
                      val legal: String? = null,
                      val warning: String? = null,
                      val storeId: String? = null) {

    fun toProductInformation(): Product {
        return Product(id = id,
                       name = name,
                       label = label,
                       owner = owner,
                       year = year,
                       model = model,
                       description = description,
                       category = category.toCategory(),
                       price = price.toPrice(),
                       stock = stock,
                       image = image.toImage(),
                       origin = origin,
                       date = date,
                       overview = overview.map { it.toInformation() },
                       keywords = keywords,
                       specifications = specifications?.toSpecifications(),
                       warranty = warranty?.toWarranty(),
                       legal = legal,
                       warning = warning,
                       storeId = storeId)
    }
}
