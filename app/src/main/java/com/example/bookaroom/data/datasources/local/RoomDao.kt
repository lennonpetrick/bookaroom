package com.example.bookaroom.data.datasources.local

import androidx.room.*
import com.example.bookaroom.data.entities.RoomEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeRooms(rooms: List<RoomEntity>): Completable

    @Query("SELECT * FROM RoomEntity WHERE spots >= 1")
    fun getAvailableRooms(): Single<List<RoomEntity>>

    @Update(entity = RoomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bookRoom(roomEntity: RoomEntity): Completable

}