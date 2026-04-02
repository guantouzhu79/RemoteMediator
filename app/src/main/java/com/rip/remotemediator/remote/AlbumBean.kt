package com.rip.remotemediator.remote

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class AlbumBean (val id: Int, val userId:Int,
                      val title: String,
                      var imgUrl: String)