package com.example.bookaroom.data.mapper

import com.example.bookaroom.data.entities.RoomEntity
import com.example.bookaroom.domain.Room
import javax.inject.Inject

internal class RoomMapper @Inject constructor() {
    fun convert(entity: RoomEntity): Room {
        return Room(entity.name, entity.spots, entity.thumbnail)
    }

    fun convert(model: Room): RoomEntity {
        return RoomEntity(model.name, model.spots, model.thumbnail)
    }
}