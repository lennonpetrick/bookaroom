package com.example.bookaroom.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookaroom.data.RoomEntity
import com.example.bookaroom.data.local.RoomDao

@Database(entities = [RoomEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}