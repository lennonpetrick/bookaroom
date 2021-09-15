package com.example.bookaroom

import android.app.Application
import com.example.bookaroom.di.Components

internal class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Components.init(this)
    }

}