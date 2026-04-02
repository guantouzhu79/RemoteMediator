package com.rip.remotemediator.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "album", indices = [Index(value = ["id"], unique = true)])
data class AlbumModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid", typeAffinity = ColumnInfo.INTEGER)
    val no: Int = 0,
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var id: Int,
    @ColumnInfo(name = "user_id", typeAffinity = ColumnInfo.INTEGER)
    var userId: Int,
    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    var title: String,
    @ColumnInfo(name = "page", typeAffinity = ColumnInfo.INTEGER)
    var page: Int,
    @ColumnInfo(name = "img_url", typeAffinity = ColumnInfo.TEXT)
    var imgUrl: String,
    @ColumnInfo(name = "create_time", typeAffinity = ColumnInfo.TEXT)
    var createTime: String,

)