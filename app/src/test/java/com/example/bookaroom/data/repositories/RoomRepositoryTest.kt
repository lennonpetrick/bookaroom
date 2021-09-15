package com.example.bookaroom.data.repositories

import com.example.bookaroom.data.datasources.local.RoomLocalDataSource
import com.example.bookaroom.data.datasources.network.RoomNetworkDataSource
import com.example.bookaroom.data.entities.RoomEntity
import com.example.bookaroom.data.mapper.RoomMapper
import com.example.bookaroom.domain.Room
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class RoomRepositoryTest {

    @Mock
    private lateinit var networkDataSource: RoomNetworkDataSource

    @Mock
    private lateinit var localDataSource: RoomLocalDataSource

    @Mock
    private lateinit var mapper: RoomMapper

    @Mock
    private lateinit var roomEntity: RoomEntity

    @Mock
    private lateinit var roomModel: Room

    @InjectMocks
    private lateinit var subject: RoomRepository

    @Test
    fun `When fetching rooms on the network, then a list is returned and stored on database`() {
        val entities = listOf(roomEntity)
        whenever(networkDataSource.getRooms()).thenReturn(Single.just(entities))
        whenever(localDataSource.storeRooms(entities)).thenReturn(Completable.complete())
        whenever(mapper.convert(roomEntity)).thenReturn(roomModel)

        subject.getAvailableRooms()
            .test()
            .assertNoErrors()
            .assertValue(listOf(roomModel))
    }

    @Test
    fun `When fetching rooms on the network and it fails, then a list is returned from database`() {
        val entities = listOf(roomEntity)
        whenever(networkDataSource.getRooms()).thenReturn(Single.error(Exception()))
        whenever(localDataSource.getAvailableRooms()).thenReturn(Single.just(entities))
        whenever(mapper.convert(roomEntity)).thenReturn(roomModel)

        subject.getAvailableRooms()
            .test()
            .assertNoErrors()
            .assertValue(listOf(roomModel))
    }

    @Test
    fun `When fetching fails on both network and local, then an error is returned`() {
        val exception = Exception()
        whenever(networkDataSource.getRooms()).thenReturn(Single.error(exception))
        whenever(localDataSource.getAvailableRooms()).thenReturn(Single.error(exception))

        subject.getAvailableRooms()
            .test()
            .assertNoValues()
            .assertError(exception)
    }

    @Test
    fun `When booking the room, then it books local and then on the network and return a completable`() {
        whenever(localDataSource.bookRoom(roomEntity)).thenReturn(Completable.complete())
        whenever(networkDataSource.bookRoom()).thenReturn(Completable.complete())
        whenever(mapper.convert(roomModel)).thenReturn(roomEntity)

        subject.bookRoom(roomModel)
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `When booking the room on the network fails, then it returns a completable anyway`() {
        whenever(localDataSource.bookRoom(roomEntity)).thenReturn(Completable.complete())
        whenever(networkDataSource.bookRoom()).thenReturn(Completable.error(Exception()))
        whenever(mapper.convert(roomModel)).thenReturn(roomEntity)

        subject.bookRoom(roomModel)
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}