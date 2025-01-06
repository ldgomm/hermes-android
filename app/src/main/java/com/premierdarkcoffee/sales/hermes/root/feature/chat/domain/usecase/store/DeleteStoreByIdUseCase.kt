package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store

//
//  DeleteStoreByIdUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 15/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.StoreRepositoriable
import javax.inject.Inject

class DeleteStoreByIdUseCase @Inject constructor(private val storeRepository: StoreRepositoriable) {

    suspend operator fun invoke(storeId: String) {
        storeRepository.deleteUserById(storeId)
    }
}
