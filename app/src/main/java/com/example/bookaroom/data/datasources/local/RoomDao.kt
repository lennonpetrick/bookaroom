package com.example.bookaroom.data.datasources.local

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bookaroom.data.entities.RoomEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface RoomDao {

    @Query("SELECT * FROM RoomEntity WHERE spots >= 1")
    fun getAvailableRooms(): Single<List<RoomEntity>>

    @Update(entity = RoomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bookRoom(roomEntity: RoomEntity): Completable

}