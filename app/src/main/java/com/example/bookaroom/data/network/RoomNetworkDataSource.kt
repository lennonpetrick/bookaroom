package com.example.bookaroom.data.network

import com.example.bookaroom.data.RoomEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class RoomNetworkDataSource @Inject constructor(private val service: ApiService) {

    fun getRooms(): Single<List<RoomEntity>> {
        return service.getRooms()
            .map { it.rooms }
    }

    fun bookRoom(): Completable {
        return service.bookRoom()
    }

}