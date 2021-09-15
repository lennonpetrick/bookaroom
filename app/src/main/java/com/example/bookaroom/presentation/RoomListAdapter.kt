package com.example.bookaroom.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookaroom.databinding.LayoutRoomsAdapterBinding
import com.squareup.picasso.Picasso

internal class RoomListAdapter(private val listener: ClickListener) : RecyclerView.Adapter<RoomListItemViewHolder>() {

    var rooms: List<RoomListView> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomListItemViewHolder {
        return RoomListItemViewHolder(LayoutRoomsAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RoomListItemViewHolder, position: Int) {
        holder.bind(rooms[position], listener)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    interface ClickListener {
        fun onBookClicked(room: RoomListView)
    }
}

internal class RoomListItemViewHolder(private val binding: LayoutRoomsAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(room: RoomListView, listener: RoomListAdapter.ClickListener) {
        with(binding) {
            Picasso.get()
                .load(room.thumbnail)
                .fit()
                .into(image)

            nameText.text = room.name
            spotsText.text = room.spotsRemaining.let { root.context.getString(it.text, it.argValue) }
            bookBtn.setOnClickListener { listener.onBookClicked(room) }
        }
    }
}