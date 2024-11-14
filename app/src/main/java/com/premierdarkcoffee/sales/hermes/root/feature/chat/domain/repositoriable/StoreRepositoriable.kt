package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity

interface StoreRepositoriable {

    suspend fun insertStore(store: StoreEntity)

    suspend fun updateStore(store: StoreEntity)

    suspend fun getUserById(storeId: String): StoreEntity?

    suspend fun getAllUsers(): List<StoreEntity>

    suspend fun deleteUserById(id: String)
}
