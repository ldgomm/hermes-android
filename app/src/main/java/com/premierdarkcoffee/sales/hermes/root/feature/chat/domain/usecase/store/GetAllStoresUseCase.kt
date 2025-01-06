package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store

//
//  GetAllStoresUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 15/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.StoreRepositoriable
import javax.inject.Inject

class GetAllStoresUseCase @Inject constructor(private val storeRepository: StoreRepositoriable) {

    suspend operator fun invoke(): List<StoreEntity> {
        return storeRepository.getAllUsers()
    }
}