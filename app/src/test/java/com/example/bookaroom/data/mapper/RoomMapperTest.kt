package com.example.bookaroom.data.mapper

import com.example.bookaroom.data.entities.RoomEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class RoomMapperTest {

    private val subject = RoomMapper()

    @Test
    fun `When converting a room entity to a model, then it converts properly`() {
        val entity = createRoomEntity()
        val model = subject.convert(entity)

        assertAll("Room fields", {
            with(model) {
                assertEquals(entity.name, name)
                assertEquals(entity.spots, spots)
                assertEquals(entity.thumbnail, thumbnail)
            }
        })
    }

    private fun createRoomEntity(): RoomEntity {
        return RoomEntity(name = "name", spots = 1, thumbnail = "imageUrl")
    }
}