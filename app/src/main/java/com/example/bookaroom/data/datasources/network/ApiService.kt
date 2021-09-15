package com.example.bookaroom.data.datasources.network

import com.example.bookaroom.data.entities.RoomEntityWrapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

internal interface ApiService {

    @GET("rooms.json")
    fun getRooms(): Single<RoomEntityWrapper>

    @GET("bookRoom.json")
    fun bookRoom(): Completable

}