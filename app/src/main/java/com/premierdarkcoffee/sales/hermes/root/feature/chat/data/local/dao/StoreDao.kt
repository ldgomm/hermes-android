package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.dao

//
//  UserDao.kt
//  Hermes
//
//  Created by José Ruiz on 7/8/24.
//

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(store: StoreEntity)

    @Update
    suspend fun updateStore(store: StoreEntity)

    @Query("SELECT * FROM store WHERE id = :storeId")
    suspend fun getStoreById(storeId: String): StoreEntity?

    @Query("SELECT * FROM store")
    suspend fun getAllStores(): List<StoreEntity>

    @Query("DELETE FROM store WHERE id = :id")
    suspend fun deleteStoreById(id: String)
}
