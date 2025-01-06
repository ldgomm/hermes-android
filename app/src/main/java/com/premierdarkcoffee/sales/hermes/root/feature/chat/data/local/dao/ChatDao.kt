package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.chat.ChatMessageEntity

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(chatMessage: ChatMessageEntity)

    @Query("SELECT * FROM chat ORDER BY date")
    suspend fun getAllChatMessages(): List<ChatMessageEntity>

    @Query("DELETE FROM chat")
    suspend fun deleteAllChatMessages()

}

