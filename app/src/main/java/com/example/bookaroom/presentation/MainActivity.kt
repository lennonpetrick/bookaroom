package com.example.bookaroom.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookaroom.R
import com.example.bookaroom.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: AvailableRoomsViewModel

    private lateinit var binding: ActivityMainBinding
    private lateinit var roomListAdapter: RoomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        setUpRecyclerView()
        AvailableRoomsComponent.init(this)

        viewModel.state().observe(this, { onStateChanges(it) })
        viewModel.getAvailableRooms()
    }

    private fun setUpRecyclerView() {
        roomListAdapter = RoomListAdapter(object : RoomListAdapter.ClickListener {
            override fun onBookClicked(room: RoomListView) {
                viewModel.bookRoom(room)
            }
        })

        binding.rooms.apply {
            adapter = roomListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    private fun onStateChanges(state: ViewState) {
        if (state != ViewState.Loading) enableLoading(false)

        when (state) {
            is ViewState.Loading -> enableLoading(true)
            is ViewState.Error -> showDefaultError()
            is ViewState.Loaded -> displayRooms(state.rooms)
            is ViewState.Booked -> notifyBooked()
        }
    }

    private fun enableLoading(param: Boolean) {
        if (param) {
            binding.progress.visibility = View.VISIBLE
            binding.rooms.visibility = View.GONE
        } else {
            binding.progress.visibility = View.GONE
            binding.rooms.visibility = View.VISIBLE
        }
    }

    private fun displayRooms(rooms: List<RoomListView>) {
        roomListAdapter.rooms = rooms
    }

    private fun notifyBooked() {
        Toast.makeText(this, getString(R.string.booked_message), Toast.LENGTH_LONG).show()
    }

    private fun showDefaultError() {
        Toast.makeText(this, getString(R.string.default_error_message), Toast.LENGTH_LONG).show()
    }

}