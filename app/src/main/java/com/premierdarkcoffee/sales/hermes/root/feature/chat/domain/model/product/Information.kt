package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.product

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.InformationEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.product.InformationDto

data class Information(val id: String,
                       val title: String,
                       val subtitle: String,
                       val description: String,
                       val image: Image,
                       val place: Int) {

    fun toInformationDto(): InformationDto {
        return InformationDto(id = id,
                              title = title,
                              subtitle = subtitle,
                              description = description,
                              image = image.toImageDto(),
                              place = place)
    }

    fun toInformationEntity(): InformationEntity {
        return InformationEntity(id = id,
                                 title = title,
                                 subtitle = subtitle,
                                 description = description,
                                 image = image.toImageEntity(),
                                 place = place)
    }
}


//    fun toInformationResultState(): InformationResultState {
//        return InformationResultState(id = id,
//                                      title = title,
//                                      subtitle = subtitle,
//                                      description = description,
//                                      path = image.path ?: "",
//                                      belongs = image.belongs,
//                                      place = place)
//    }