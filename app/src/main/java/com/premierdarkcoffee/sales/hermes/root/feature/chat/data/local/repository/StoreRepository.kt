package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.repository

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.database.MainDatabase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.StoreRepositoriable
import javax.inject.Inject

class StoreRepository @Inject constructor(private val database: MainDatabase) : StoreRepositoriable {

    override suspend fun insertStore(store: StoreEntity) {
        // Insert the store entity into the local database
        val result = database.storeDao.getStoreById(storeId = store.id)
        if (result == null) {
            database.storeDao.insertStore(store)
        } else {
            database.storeDao.updateStore(store)
        }
    }

    override suspend fun updateStore(store: StoreEntity) {
        // Update the store entity in the local database
        database.storeDao.updateStore(store)
    }

    override suspend fun getUserById(storeId: String): StoreEntity? {
        // Fetch a store entity by its ID from the local database
        return database.storeDao.getStoreById(storeId)
    }

    override suspend fun getAllUsers(): List<StoreEntity> {
        // Fetch all store entities from the local database
        return database.storeDao.getAllStores()
    }

    override suspend fun deleteUserById(id: String) {
        // Delete a store entity by its ID from the local database
        database.storeDao.deleteStoreById(id)
    }
}
