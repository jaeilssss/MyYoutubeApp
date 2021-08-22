package com.example.myyoutubeapp.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myyoutubeapp.model.VideoModel
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil

class VideoAdapter : ListAdapter<VideoModel,VideoAdapter.ViewHolder>(diffUtil){


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){


        fun bind(item : VideoModel){


        }
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<VideoModel>(){
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem ==newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}