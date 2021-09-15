package com.example.bookaroom.di.modules

import android.content.Context
import androidx.room.Room
import com.example.bookaroom.data.datasources.local.RoomDao
import com.example.bookaroom.data.datasources.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DatabaseModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun providesAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "bookaroom.db")
            .build()
    }

    @Provides
    @Singleton
    fun providesRoomDao(database: AppDatabase): RoomDao {
        return database.roomDao()
    }
}