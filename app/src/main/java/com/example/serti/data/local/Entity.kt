package com.example.serti.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val id: Int = 0,
    val token: String
)



@Entity(tableName = "search_keyword")
data class KeywordEntity(
    @PrimaryKey val keyword: String
)