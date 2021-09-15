package com.example.bookaroom.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookaroom.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: AvailableRoomsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AvailableRoomsComponent.init(this)
    }
}