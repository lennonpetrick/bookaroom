package com.example.bookaroom.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookaroom.data.datasources.local.RoomDao
import com.example.bookaroom.data.entities.RoomEntity

@Database(entities = [RoomEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}