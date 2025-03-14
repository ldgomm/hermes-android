package com.premierdarkcoffee.sales.hermes.root.di

import android.content.Context
import androidx.room.Room
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.data.remote.AuthenticationService
import com.premierdarkcoffee.sales.hermes.root.feature.authentication.domain.serviceable.AuthenticationServiceable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.database.MainDatabase
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.repository.CartRepository
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.repository.ChatRepository
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.repository.MessageRepository
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.repository.StoreRepository
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.service.PhraseService
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.service.ProductService
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.service.StoreService
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.CartRepositoriable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.ChatRepositoriable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.MessageRepositoriable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.repositoriable.StoreRepositoriable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.PhraseServiceable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.ProductServiceable
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.serviceable.StoreServiceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyInjection {

    @Singleton
    @Provides
    fun provideMainDatabase(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(context, MainDatabase::class.java, "main_database").build()
    }

    @Singleton
    @Provides
    fun provideChatRepositoriable(database: MainDatabase): ChatRepositoriable {
        return ChatRepository(database)
    }

    @Singleton
    @Provides
    fun provideCartRepositoriable(database: MainDatabase): CartRepositoriable {
        return CartRepository(database)
    }

    @Singleton
    @Provides
    fun provideMessageRepositoriable(database: MainDatabase): MessageRepositoriable {
        return MessageRepository(database)
    }

    @Singleton
    @Provides
    fun provideStoreRepositoriable(database: MainDatabase): StoreRepositoriable {
        return StoreRepository(database)
    }

//    @Singleton
//    @Provides
//    fun provideUserRepositoriable(database: MainDatabase): UserRepositoriable {
//        return UserRepository(database)
//    }
    //TODO inject shared preferences or data store

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    allowStructuredMapKeys = true
                    prettyPrint = true
                })
            }
            install(Logging) { level = LogLevel.ALL }
        }
    }

    @Singleton
    @Provides
    fun provideClientServiceable(httpClient: HttpClient): ProductServiceable {
        return ProductService(httpClient)
    }

    @Singleton
    @Provides
    fun provideStoreServiceable(httpClient: HttpClient): StoreServiceable {
        return StoreService(httpClient)
    }

    @Singleton
    @Provides
    fun provideAuthenticationServiceable(httpClient: HttpClient): AuthenticationServiceable {
        return AuthenticationService(httpClient)
    }

    @Singleton
    @Provides
    fun provideDataServiceable(httpClient: HttpClient): PhraseServiceable {
        return PhraseService(httpClient)
    }
}

//    private fun deleteDatabase(context: Context, databaseClass: Class<out RoomDatabase>) {
//        context.deleteDatabase(Room.databaseBuilder(context, databaseClass, "photo_database").fallbackToDestructiveMigration()
//                                   .build().openHelper.databaseName)
//    }
