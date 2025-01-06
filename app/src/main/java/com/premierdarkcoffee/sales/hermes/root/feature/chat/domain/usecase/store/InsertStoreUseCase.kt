package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store

//
//  InsertStoreUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 15/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.StoreRepositoriable
import javax.inject.Inject

class InsertStoreUseCase @Inject constructor(private val storeRepository: StoreRepositoriable) {

    suspend operator fun invoke(store: StoreEntity) {
        storeRepository.insertStore(store)
    }
}