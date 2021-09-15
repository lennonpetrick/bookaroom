package com.example.bookaroom.domain

import androidx.room.PrimaryKey

internal class Room(
    val name: String,
    val spots: Int,
    val thumbnail: String
)