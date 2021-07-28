package com.alecbrando.animeapp.ui.fragments.Detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alecbrando.animeapp.databinding.FragmentDetailBinding
import com.alecbrando.animeapp.ui.viewmodels.DetailEvents
import com.alecbrando.animeapp.ui.viewmodels.DetailViewModel
import com.alecbrando.animeapp.utils.Status
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel : DetailViewModel by viewModels()
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.res.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    binding.apply {
                        progressLoading.visibility = View.INVISIBLE
                        title.text = it.data?.title
                        title.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(it.data?.image_url).into(imageview)
                        imageview.visibility = View.VISIBLE
                        score.text = "Score: ${it.data?.score.toString()}"
                        score.visibility = View.VISIBLE
                        rank.text ="Rank: ${it.data?.rank.toString()}"
                        rank.visibility = View.VISIBLE
                        popularity.text = "Popularity: ${it.data?.popularity.toString()}"
                        popularity.visibility = View.VISIBLE
                        favoriteIconRed.visibility = if(viewModel.favorited){
                           View.VISIBLE
                        } else {
                            View.INVISIBLE
                        }
                        favoriteIcon.visibility = if(!viewModel.favorited){
                            View.VISIBLE
                        } else {
                            View.INVISIBLE
                        }
                        synopsis.text = it.data?.synopsis
                        synopsis.visibility = View.VISIBLE
                        favoriteIcon.setOnClickListener {
                            viewModel.addAnimeToFavorites()
                            favoriteIconRed.visibility = View.VISIBLE
                            favoriteIcon.visibility = View.INVISIBLE
                        }
                        favoriteIconRed.setOnClickListener {
                            viewModel.removeFromFavorites()
                            favoriteIcon.visibility = View.VISIBLE
                            favoriteIconRed.visibility = View.INVISIBLE
                        }
                    }
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.detailFlowEvents.collect {
                when (it) {
                    is DetailEvents.AddedToFavorites -> {
                        Toast.makeText(requireContext(), "${it.anime.title} was Added to your favorites!", Toast.LENGTH_SHORT).show()
                    }
                    is DetailEvents.RemovedFromFavorites -> {
                        Toast.makeText(requireContext(), "${it.anime.title} was removed from your favorites!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}