package com.alecbrando.animeapp.ui.fragments.Favorites


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alecbrando.animeapp.data.models.AnimeDetail
import com.alecbrando.animeapp.databinding.AnimeListHolderHomeBinding
import com.bumptech.glide.Glide


class FavoriteAdapter(private val listener: OnClickListener) : ListAdapter<AnimeDetail, FavoriteAdapter.AnimeListHolder>(DiffCallback()) {

    inner class AnimeListHolder(private val binding: AnimeListHolderHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                imageView.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val AnimeDetail = getItem(position)
                        listener.itemClicked(AnimeDetail)
                    }
                }
            }
        }

        fun bind(anime: AnimeDetail) {
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
        fun itemClicked(anime: AnimeDetail)
    }

    class DiffCallback : DiffUtil.ItemCallback<AnimeDetail>() {
        override fun areItemsTheSame(oldItem: AnimeDetail, newItem: AnimeDetail) = oldItem.mal_id == newItem.mal_id


        override fun areContentsTheSame(oldItem: AnimeDetail, newItem: AnimeDetail) = oldItem == newItem
    }


}