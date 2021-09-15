package com.example.bookaroom.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.bookaroom.data.repositories.RoomRepository
import com.example.bookaroom.di.AppComponent
import com.example.bookaroom.di.Components
import com.example.bookaroom.di.FeatureScope
import com.example.bookaroom.di.qualifiers.IOScheduler
import com.example.bookaroom.di.qualifiers.UIScheduler
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

@Module
internal class AvailableRoomsModule(private val owner: ViewModelStoreOwner) {

    @Provides
    fun providesAvailableRoomsViewModel(factory: AvailableRoomsViewModelFactory): AvailableRoomsViewModel {
        return ViewModelProvider(owner, factory).get(AvailableRoomsViewModel::class.java)
    }

}

@FeatureScope
@Component(dependencies = [AppComponent::class], modules = [AvailableRoomsModule::class])
internal interface AvailableRoomsComponent {
    fun inject(activity: MainActivity)

    companion object {
        fun init(activity: MainActivity) {
            DaggerAvailableRoomsComponent.builder()
                .appComponent(Components.appComponent())
                .availableRoomsModule(AvailableRoomsModule(activity))
                .build()
                .inject(activity)
        }
    }
}

internal class AvailableRoomsViewModelFactory @Inject constructor(
    private val repository: RoomRepository,
    private val mapper: RoomListViewMapper,
    @IOScheduler private val workScheduler: Scheduler,
    @UIScheduler private val uiScheduler: Scheduler
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvailableRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AvailableRoomsViewModel(repository, mapper, workScheduler, uiScheduler) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}