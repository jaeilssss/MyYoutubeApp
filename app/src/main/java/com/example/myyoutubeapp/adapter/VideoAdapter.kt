package com.example.myyoutubeapp.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myyoutubeapp.model.VideoModel
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.myyoutubeapp.R

class VideoAdapter : ListAdapter<VideoModel,VideoAdapter.ViewHolder>(diffUtil){


    inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view){


        fun bind(item : VideoModel){

            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val subTitleTextView = view.findViewById<TextView>(R.id.subTitleTextView)
            val thumbnailImageView = view.findViewById<ImageView>(R.id.thumnailImageView)

            titleTextView.text =item.title

            subTitleTextView.text = item.subtitle

            Glide.with(view).load(item.thumb).into(thumbnailImageView)

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
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       return holder.bind(currentList[position])
    }
}