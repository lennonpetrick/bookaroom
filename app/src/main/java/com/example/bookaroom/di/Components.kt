package com.example.bookaroom.di

import android.content.Context
import com.example.bookaroom.di.modules.DatabaseModule
import com.example.bookaroom.di.modules.NetworkModule
import com.example.bookaroom.di.modules.SchedulerModule

internal object Components {

    private lateinit var appComponent: AppComponent

    fun init(applicationContext: Context) {
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .schedulerModule(SchedulerModule())
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }

    fun appComponent() = appComponent

}