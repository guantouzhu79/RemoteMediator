package com.rip.remotemediator.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AlbumDao {

    // replace 替换也可以
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrUpdate(albums:List<AlbumModel>): List<Long>

    @Query("DELETE FROM album")
    suspend fun clearAlbums(): Int

    @Query("SELECT * FROM album ORDER BY uid")
    fun getAlbums(): PagingSource<Int, AlbumModel>

    @Transaction
    suspend fun refreshAlbums(albums: List<AlbumModel>): List<Long> {
        clearAlbums()
        return insertOrUpdate(albums)
    }
}