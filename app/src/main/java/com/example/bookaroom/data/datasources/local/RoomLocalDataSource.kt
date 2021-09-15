package com.example.bookaroom.data.datasources.local

import com.example.bookaroom.data.entities.RoomEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class RoomLocalDataSource @Inject constructor(private val roomDao: RoomDao) {

    fun storeRooms(rooms: List<RoomEntity>): Completable {
        return roomDao.storeRooms(rooms)
    }

    fun getAvailableRooms(): Single<List<RoomEntity>> {
        return roomDao.getAvailableRooms()
    }

    fun bookRoom(roomEntity: RoomEntity): Completable {
        val newEntity = roomEntity.copy(spots = roomEntity.spots - 1)
        return roomDao.bookRoom(newEntity)
    }

}