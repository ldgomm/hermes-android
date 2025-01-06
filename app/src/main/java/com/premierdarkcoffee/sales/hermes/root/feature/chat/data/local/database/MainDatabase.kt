package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.dao.CartDao
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.dao.ChatDao
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.dao.MessageDao
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.dao.StoreDao
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.cart.CartEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.AddressConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.CategoryConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.ChatMessageConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.GeoPointConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.ImageConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.InformationConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.MessageConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.PriceConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.ProductConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.ProductListConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.SpecificationsConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.StoreInformationConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.StoreStatusConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.StringConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.converter.WarrantyConverter
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.message.MessageEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreEntity

@Database(
    entities = [ChatMessageEntity::class, MessageEntity::class, CartEntity::class, StoreEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ChatMessageConverter::class,
    ProductListConverter::class,
    CategoryConverter::class,
    ImageConverter::class,
    InformationConverter::class,
    PriceConverter::class,
    SpecificationsConverter::class,
    StringConverter::class,
    WarrantyConverter::class,
    StoreInformationConverter::class,
    AddressConverter::class,
    GeoPointConverter::class,
    MessageConverter::class,
    ProductConverter::class,
    StoreStatusConverter::class,
)
abstract class MainDatabase : RoomDatabase() {
    abstract val chatDao: ChatDao
    abstract val messageDao: MessageDao
    abstract val cartDao: CartDao
    abstract val storeDao: StoreDao
}
