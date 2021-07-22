package com.alecbrando.animeapp.ui.fragments.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alecbrando.animeapp.data.api.models.Anime
import com.alecbrando.animeapp.databinding.AnimeListHolderHomeBinding
import com.bumptech.glide.Glide


class FeedAdapter(private val listener: OnClickListener) : ListAdapter<Anime, FeedAdapter.AnimeListHolder>(DiffCallback()) {

    inner class AnimeListHolder(private val binding: AnimeListHolderHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                imageView.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val anime = getItem(position)
                        listener.itemClicked(anime)
                    }
                }
            }
        }

        fun bind(anime: Anime) {
            binding.apply {
                Glide.with(root).load(anime.image_url).into(imageView)
                textView.text = anime.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListHolder {
        val binding = AnimeListHolderHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnClickListener {
        fun itemClicked(anime: Anime)
    }

    class DiffCallback : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime) = oldItem.mal_id == newItem.mal_id


        override fun areContentsTheSame(oldItem: Anime, newItem: Anime) = oldItem == newItem
    }

}