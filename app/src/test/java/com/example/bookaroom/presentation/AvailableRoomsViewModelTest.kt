package com.example.bookaroom.presentation

import com.example.bookaroom.data.repositories.RoomRepository
import com.example.bookaroom.domain.Room
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class, InstantExecutorExtension::class)
internal class AvailableRoomsViewModelTest {

    @Mock
    private lateinit var repository: RoomRepository

    @Mock
    private lateinit var mapper: RoomListViewMapper

    private lateinit var subject: AvailableRoomsViewModel

    private val scheduler = Schedulers.trampoline()

    @BeforeEach
    fun setUp() {
        subject = AvailableRoomsViewModel(repository, mapper, scheduler, scheduler)
    }

    @Test
    fun `When getting available rooms succeeds, then a list is returned`() {
        val room = mock<Room>()
        val roomListView = mock<RoomListView>()
        whenever(repository.getAvailableRooms()).thenReturn(Single.just(listOf(room)))
        whenever(mapper.convert(room)).thenReturn(roomListView)

        val state = subject.state().observe()
        subject.getAvailableRooms()

        state.assertValueAt(0) { it is ViewState.Loading }
            .assertLastValue {
                it is ViewState.Loaded
                        && it.rooms == listOf(roomListView)
            }
    }

    @Test
    fun `When getting available rooms fails, then an error is emitted`() {
        whenever(repository.getAvailableRooms()).thenReturn(Single.error(Throwable()))

        val state = subject.state().observe()
        subject.getAvailableRooms()

        state.assertValueAt(0) { it is ViewState.Loading }
            .assertLastValue { it is ViewState.Error }
    }

    @Test
    fun `When booking a room succeeds, then a booked state is emitted`() {
        val room = mock<Room> {
            on { name } doReturn "name"
        }
        val roomListView = mock<RoomListView> {
            on { name } doReturn "name"
        }
        whenever(repository.getAvailableRooms()).thenReturn(Single.just(listOf(room)))
        whenever(repository.bookRoom(room)).thenReturn(Completable.complete())
        whenever(mapper.convert(room)).thenReturn(roomListView)

        val state = subject.state().observe()
        subject.getAvailableRooms()
        subject.bookRoom(roomListView)

        state.assertLastValue { it is ViewState.Booked }
    }

    @Test
    fun `When booking a room fails, then an error is emitted`() {
        val room = mock<Room> {
            on { name } doReturn "name"
        }
        val roomListView = mock<RoomListView> {
            on { name } doReturn "name"
        }
        whenever(repository.getAvailableRooms()).thenReturn(Single.just(listOf(room)))
        whenever(repository.bookRoom(room)).thenReturn(Completable.error(Throwable()))
        whenever(mapper.convert(room)).thenReturn(roomListView)

        val state = subject.state().observe()
        subject.getAvailableRooms()
        subject.bookRoom(roomListView)

        state.assertLastValue { it is ViewState.Error }
    }
}