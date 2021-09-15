package com.example.bookaroom.data.mapper

import com.example.bookaroom.data.entities.RoomEntity
import com.example.bookaroom.domain.Room
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

    @Test
    fun `When converting a room model to an entity, then it converts properly`() {
        val model = createRoomModel()
        val entity = subject.convert(model)

        assertAll("Room fields", {
            with(entity) {
                assertEquals(model.name, name)
                assertEquals(model.spots, spots)
                assertEquals(model.thumbnail, thumbnail)
            }
        })
    }

    private fun createRoomEntity(): RoomEntity {
        return RoomEntity(name = "name", spots = 1, thumbnail = "imageUrl")
    }

    private fun createRoomModel(): Room {
        return Room(name = "name", spots = 1, thumbnail = "imageUrl")
    }
}