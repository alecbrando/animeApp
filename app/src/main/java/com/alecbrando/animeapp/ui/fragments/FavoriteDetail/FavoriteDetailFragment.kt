package com.alecbrando.animeapp.ui.fragments.FavoriteDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alecbrando.animeapp.databinding.FragmentDetailBinding
import com.alecbrando.animeapp.ui.viewmodels.FavoriteDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteDetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewmodel : FavoriteDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.apply {
            title.text = viewmodel.getAnime?.title
            Glide.with(requireContext()).load(viewmodel.getAnime?.image_url).into(imageview)
            score.text = viewmodel.getAnime?.score.toString()
            rank.text = "Rank: ${viewmodel.getAnime?.rank.toString()}"
            popularity.text = "Popularity: ${viewmodel.getAnime?.popularity.toString()}"
            synopsis.text = viewmodel.getAnime?.synopsis
            favoriteIcon.visibility = View.INVISIBLE
            favoriteIconRed.visibility = View.VISIBLE
            favoriteIconRed.setOnClickListener {
                viewmodel.removeAnimeFromFavorites()
                findNavController().navigate(FavoriteDetailFragmentDirections.actionFavoriteDetailFragmentToFavoritesFragment())
            }
        }


        return binding.root
    }


}