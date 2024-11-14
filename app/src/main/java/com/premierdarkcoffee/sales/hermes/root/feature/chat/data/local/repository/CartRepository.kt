package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.repository

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.database.MainDatabase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.cart.CartEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.product.ProductEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.CartRepositoriable
import javax.inject.Inject

class CartRepository @Inject constructor(private val database: MainDatabase) : CartRepositoriable {

    /**
     * Creates a new product information entry and adds it to the cart.
     *
     * @param product The [ProductEntity] object representing the product to be added.
     */
    override suspend fun createProductInformation(product: ProductEntity) {
        val cartEntity = CartEntity(id = product.id, product = product, date = System.currentTimeMillis())
        database.cartDao.insertCartProduct(cartEntity)
    }

    /**
     * Reads all product information entries from the cart.
     *
     * @return A list of [CartEntity] objects representing all products in the cart.
     */
    override suspend fun readProductsInformation(): List<CartEntity> {
        return database.cartDao.getAllCartProducts()
    }

    /**
     * Deletes a product from the cart based on the product ID.
     *
     * @param id The ID of the product to be deleted.
     */
    override suspend fun deleteProduct(id: String) {
        database.cartDao.deleteCartProduct(id = id)
    }
}
