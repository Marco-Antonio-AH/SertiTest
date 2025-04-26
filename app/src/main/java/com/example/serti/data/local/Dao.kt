    package com.example.serti.data.local

    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface SessionDao {
        @Query("SELECT * FROM session WHERE id = 0")
        suspend fun getSession(): SessionEntity?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun saveSession(session: SessionEntity)

        @Query("DELETE FROM session")
        suspend fun clearSession()
    }

    @Dao
    interface KeywordDao {
        @Query("SELECT * FROM search_keyword")
        fun getAll(): Flow<List<KeywordEntity>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(keyword: KeywordEntity)


        @Query("DELETE FROM search_keyword WHERE keyword = :kw")
        suspend fun delete(kw: String)
    }