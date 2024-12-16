package dev.samu.bladerecycle.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkTypeDao {
    @Query("SELECT * FROM bookmark_type_table")
    fun getAllBookmarkTypes(): Flow<List<BookmarkType>>

    @Insert
    suspend fun insert(bookmarkType: BookmarkType)

    @Update
    suspend fun update(bookmarkType: BookmarkType)

    @Delete
    suspend fun delete(bookmarkType: BookmarkType)
}