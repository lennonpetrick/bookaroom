package com.example.bookaroom.data.repositories

import com.example.bookaroom.data.datasources.local.RoomLocalDataSource
import com.example.bookaroom.data.datasources.network.RoomNetworkDataSource
import com.example.bookaroom.data.entities.RoomEntity
import com.example.bookaroom.data.mapper.RoomMapper
import com.example.bookaroom.domain.Room
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RoomRepository @Inject constructor(
    private val networkDataSource: RoomNetworkDataSource,
    private val localDataSource: RoomLocalDataSource,
    private val mapper: RoomMapper,
) {

    fun getAvailableRooms(): Single<List<Room>> {
        return getRoomsOnNetwork()
            .onErrorResumeNext { localDataSource.getAvailableRooms() }
            .flattenAsObservable { it }
            .map(mapper::convert)
            .toList()
    }

    fun bookRoom(room: Room): Completable {
        return localDataSource.bookRoom(mapper.convert(room))
            .andThen(networkDataSource.bookRoom().onErrorResumeNext { Completable.complete() })
    }

    private fun getRoomsOnNetwork(): Single<List<RoomEntity>> {
        return networkDataSource.getRooms()
            .flatMap { localDataSource.storeRooms(it).toSingleDefault(it) }
    }

}