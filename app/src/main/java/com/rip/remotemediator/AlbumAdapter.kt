package com.rip.remotemediator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rip.remotemediator.databinding.ItemAlbumRecycleBinding
import com.rip.remotemediator.local.AlbumModel

class AlbumAdapter: PagingDataAdapter<AlbumModel, AlbumAdapter.AlbumViewHolder>(
    object : DiffUtil.ItemCallback<AlbumModel>(){
        override fun areItemsTheSame(
            oldItem: AlbumModel,
            newItem: AlbumModel
        ): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AlbumModel,
            newItem: AlbumModel
        ): Boolean {
            return (oldItem.id == newItem.id && oldItem.userId == newItem.userId
                    && oldItem.title == newItem.title)
        }
    }) {

    inner class AlbumViewHolder(val binding: ItemAlbumRecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumViewHolder {
        val binding: ItemAlbumRecycleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
            parent.context),R.layout.item_album_recycle,parent,false
        )
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AlbumViewHolder,
        position: Int
    ) {
         val album = getItem(position)
         holder.binding.album = album
    }


}