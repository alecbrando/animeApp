package com.alecbrando.animeapp.ui.fragments.FavoriteDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alecbrando.animeapp.databinding.FragmentDetailBinding
import com.alecbrando.animeapp.ui.viewmodels.FavoriteEvent
import com.alecbrando.animeapp.ui.viewmodels.FavoritesViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class FavoriteDetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewmodel : FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.apply {
            progressLoading.visibility = View.INVISIBLE
            title.text = viewmodel.getAnime?.title
            title.visibility = View.VISIBLE
            Glide.with(requireContext()).load(viewmodel.getAnime?.image_url).into(imageview)
            imageview.visibility = View.VISIBLE
            score.text = viewmodel.getAnime?.score.toString()
            score.visibility = View.VISIBLE
            rank.text = "Rank: ${viewmodel.getAnime?.rank.toString()}"
            rank.visibility = View.VISIBLE
            popularity.text = "Popularity: ${viewmodel.getAnime?.popularity.toString()}"
            popularity.visibility = View.VISIBLE
            synopsis.text = viewmodel.getAnime?.synopsis
            synopsis.visibility = View.VISIBLE
            favoriteIcon.visibility = View.INVISIBLE
            favoriteIconRed.visibility = View.VISIBLE
            favoriteIconRed.setOnClickListener {
                viewmodel.removeAnimeFromFavorites()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewmodel.flowFavoriteEvents.collect {
                when (it) {
                    is FavoriteEvent.NavigateBackToFavoriteWithMessage -> {
                        Toast.makeText(requireContext(), "${it.anime.title} was removed from your favorites!", Toast.LENGTH_LONG).show()
                        findNavController().navigate(FavoriteDetailFragmentDirections.actionFavoriteDetailFragmentToFavoritesFragment())
                    }
                }
            }
        }


        return binding.root
    }


}