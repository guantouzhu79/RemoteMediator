package com.rip.remotemediator.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AlbumModel::class], version = 1, exportSchema = false)
 abstract class MyDatabase() : RoomDatabase() {


    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
        const val DATABASE_NAME = "kuma.db"

        @JvmStatic
        fun getInstance(context: Application): MyDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    val instance: MyDatabase = Room.databaseBuilder(
                        context,
                        MyDatabase::class.java, DATABASE_NAME
                    ).build()
                    INSTANCE = instance
                }

            }
            return INSTANCE!!
        }

    }
    abstract fun getAlbumDao(): AlbumDao
}