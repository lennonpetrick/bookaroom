package com.example.bookaroom.data

import androidx.room.Entity
import androidx.room.PrimaryKey

internal class RoomEntityWrapper(val rooms: List<RoomEntity>)

@Entity
internal data class RoomEntity(
    @PrimaryKey
    val name: String,
    val spots: Int,
    val thumbnail: String
)