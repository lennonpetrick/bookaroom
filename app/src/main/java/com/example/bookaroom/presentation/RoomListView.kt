package com.example.bookaroom.presentation

import com.example.bookaroom.R
import com.example.bookaroom.domain.Room
import javax.inject.Inject

internal data class RoomListView(
    val name: String,
    val spotsRemaining: DisplayText,
    val thumbnail: String
)

internal class RoomListViewMapper @Inject constructor() {
    fun convert(room: Room): RoomListView {
        val spotsRemaining = DisplayText(R.string.spots_remaining, room.spots)
        return RoomListView(room.name, spotsRemaining, room.thumbnail)
    }
}