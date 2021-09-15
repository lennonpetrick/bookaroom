package com.example.bookaroom.data.datasources.local

import com.example.bookaroom.data.entities.RoomEntity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class RoomLocalDataSourceTest {

    @Mock
    private lateinit var roomDao: RoomDao

    @InjectMocks
    private lateinit var subject: RoomLocalDataSource

    @Test
    fun `When getting available rooms, then a list is returned`() {
        val rooms = listOf(mock<RoomEntity>())
        whenever(roomDao.getAvailableRooms()).thenReturn(Single.just(rooms))

        subject.getAvailableRooms()
            .test()
            .assertNoErrors()
            .assertValue(rooms)
    }

    @Test
    fun `When getting rooms fails, then an error is returned`() {
        val exception = Exception()
        whenever(roomDao.getAvailableRooms()).thenReturn(Single.error(exception))

        subject.getAvailableRooms()
            .test()
            .assertNoValues()
            .assertError(exception)
    }

    @Test
    fun `When booking the room, then a completable it returned`() {
        val room = mock<RoomEntity>()
        val newEntity = mock<RoomEntity>()
        whenever(room.copy(spots = room.spots - 1)).thenReturn(newEntity)
        whenever(roomDao.bookRoom(newEntity)).thenReturn(Completable.complete())

        subject.bookRoom(room)
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `When booking the room fails, then an error is returned`() {
        val exception = Exception()
        val room = mock<RoomEntity>()
        val newEntity = mock<RoomEntity>()
        whenever(room.copy(spots = room.spots - 1)).thenReturn(newEntity)
        whenever(roomDao.bookRoom(newEntity)).thenReturn(Completable.error(exception))

        subject.bookRoom(room)
            .test()
            .assertNoValues()
            .assertError(exception)
    }

}