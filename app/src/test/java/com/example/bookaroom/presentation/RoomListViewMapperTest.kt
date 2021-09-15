package com.example.bookaroom.presentation

import com.example.bookaroom.R
import com.example.bookaroom.domain.Room
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class RoomListViewMapperTest {

    private val subject = RoomListViewMapper()

    @Test
    fun `When converting the model to the view , then it converts properly`() {
        val model = createModel()

        val view = subject.convert(model)

        assertEquals(model.name, view.name)
        assertEquals(model.thumbnail, view.thumbnail)
        assertEquals(R.string.spots_remaining, view.spotsRemaining.text)
        assertEquals(model.spots, view.spotsRemaining.argValue)
    }

    private fun createModel(): Room {
        return Room(name = "name", spots = 2, thumbnail = "thumbnail")
    }

}