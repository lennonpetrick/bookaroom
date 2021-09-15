package com.example.bookaroom.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookaroom.data.repositories.RoomRepository
import com.example.bookaroom.domain.Room
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

internal class AvailableRoomsViewModel(
    private val repository: RoomRepository,
    private val mapper: RoomListViewMapper,
    private val workScheduler: Scheduler,
    private val uiScheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<ViewState>()
    private lateinit var rooms: List<Room>

    fun state(): LiveData<ViewState> = _state

    fun getAvailableRooms() {
        postState(ViewState.Loading)
        repository.getAvailableRooms()
            .doOnSuccess { rooms = it }
            .flattenAsObservable { it }
            .map(mapper::convert)
            .toList()
            .map(ViewState::Loaded)
            .applySchedulers()
            .subscribe(::postState) { postState(ViewState.Error) }
            .disposeInOnCleared()
    }

    fun bookRoom(roomView: RoomListView) {
        val room = rooms.first { it.name == roomView.name }
        repository.bookRoom(room)
            .toSingle { ViewState.Booked }
            .applySchedulers()
            .subscribe(::postState) { postState(ViewState.Error) }
            .disposeInOnCleared()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun postState(viewState: ViewState) {
        _state.value = viewState
    }

    private fun Disposable.disposeInOnCleared(): Disposable {
        return this.also { compositeDisposable.add(it) }
    }

    private fun <T> Single<T>.applySchedulers(): Single<T> {
        return this.subscribeOn(workScheduler)
            .observeOn(uiScheduler)
    }

}

internal sealed class ViewState {
    internal object Loading : ViewState()
    internal object Error : ViewState()
    internal class Loaded(val rooms: List<RoomListView>) : ViewState()
    internal object Booked : ViewState()
}