package com.example.bookaroom.di

import com.example.bookaroom.data.repositories.RoomRepository
import com.example.bookaroom.di.modules.DatabaseModule
import com.example.bookaroom.di.modules.NetworkModule
import com.example.bookaroom.di.modules.SchedulerModule
import com.example.bookaroom.di.qualifiers.IOScheduler
import com.example.bookaroom.di.qualifiers.UIScheduler
import dagger.Component
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, SchedulerModule::class])
internal interface AppComponent {

    @IOScheduler
    fun ioScheduler(): Scheduler

    @UIScheduler
    fun uiScheduler(): Scheduler

    fun roomRepository(): RoomRepository

}