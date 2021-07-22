package com.alecbrando.animeapp.ui.fragments.Detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alecbrando.animeapp.databinding.FragmentDetailBinding
import com.alecbrando.animeapp.ui.viewmodels.DetailViewModel
import com.alecbrando.animeapp.utils.Status
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


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
    ): View? {
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
                        title.text = it.data!!.title
                        Glide.with(requireContext()).load(it.data.image_url).into(imageview)
                        score.text = "Score: ${it.data.score.toString()}"
                        rank.text ="Rank: ${it.data.rank.toString()}"
                        popularity.text = "Popularity: ${it.data.popularity.toString()}"
                        synopsis.text = it.data.synopsis
                    }

                    Log.d("Detail",it.data!!.title)
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }
    }

}