package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.cart.CartEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(cartEntity: CartEntity)

    @Query("SELECT * FROM cart order by date")
    suspend fun getAllCartProducts(): List<CartEntity>

    @Query("delete from cart where id = :id")
    suspend fun deleteCartProduct(id: String)

}
