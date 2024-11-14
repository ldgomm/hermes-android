package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.usecase.store

//
//  GetStoreByIdUseCase.kt
//  Hermes
//
//  Created by José Ruiz on 5/8/24.
//

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.StoreDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.StoreServiceable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoreByIdUseCase @Inject constructor(private val storeServiceable: StoreServiceable) {

    operator fun invoke(url: String): Flow<Result<StoreDto>> {
        return storeServiceable.getStoreById(url)
    }
}