package com.alecbrando.animeapp.ui.fragments.Favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alecbrando.animeapp.data.models.AnimeDetail
import com.alecbrando.animeapp.databinding.FragmentFavoritesBinding
import com.alecbrando.animeapp.ui.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoriteAdapter.OnClickListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        val adapter = FavoriteAdapter(this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.favoriteAnime.observe(viewLifecycleOwner){ data ->
            adapter.submitList(data)
        }
        return binding.root
    }

    override fun itemClicked(anime: AnimeDetail) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToFavoriteDetailFragment(anime)
        findNavController().navigate(action)

    }

}