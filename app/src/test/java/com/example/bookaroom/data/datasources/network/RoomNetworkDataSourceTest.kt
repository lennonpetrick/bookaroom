package com.example.bookaroom.data.datasources.network

import com.example.bookaroom.data.entities.RoomEntity
import com.example.bookaroom.data.entities.RoomEntityWrapper
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
internal class RoomNetworkDataSourceTest {

    @Mock
    private lateinit var service: ApiService

    @InjectMocks
    private lateinit var subject: RoomNetworkDataSource

    @Test
    fun `When fetching rooms, then a list is returned`() {
        val rooms = listOf(mock<RoomEntity>())
        whenever(service.getRooms()).thenReturn(Single.just(RoomEntityWrapper(rooms)))

        subject.getRooms()
            .test()
            .assertNoErrors()
            .assertValue(rooms)
    }

    @Test
    fun `When fetching rooms fails, then an error is returned`() {
        val exception = Exception()
        whenever(service.getRooms()).thenReturn(Single.error(exception))

        subject.getRooms()
            .test()
            .assertNoValues()
            .assertError(exception)
    }

    @Test
    fun `When booking a room, then a completable it returned`() {
        whenever(service.bookRoom()).thenReturn(Completable.complete())

        subject.bookRoom()
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `When booking a room fails, then an error is returned`() {
        val exception = Exception()
        whenever(service.bookRoom()).thenReturn(Completable.error(exception))

        subject.bookRoom()
            .test()
            .assertNoValues()
            .assertError(exception)
    }

}