package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable

//
//  StoreServiceable.kt
//  Maia
//
//  Created by José Ruiz on 31/7/24.
//

import com.google.api.Endpoint
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.StoreDto
import kotlinx.coroutines.flow.Flow

interface StoreServiceable {

    fun getStoreById(endpoint: String): Flow<Result<StoreDto>>

}