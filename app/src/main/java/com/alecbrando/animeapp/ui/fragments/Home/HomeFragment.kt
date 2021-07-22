package com.alecbrando.animeapp.ui.fragments.Home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alecbrando.animeapp.R
import com.alecbrando.animeapp.data.api.models.Anime
import com.alecbrando.animeapp.databinding.FragmentHomeBinding
import com.alecbrando.animeapp.ui.viewmodels.AnimeEvent
import com.alecbrando.animeapp.ui.viewmodels.HomeViewModel
import com.alecbrando.animeapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : Fragment(), FeedAdapter.OnClickListener {

    private val viewModel : HomeViewModel by viewModels()

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FeedAdapter(this)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.res.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    adapter.submitList(it.data!!.top)
                }
                Status.ERROR -> {
                    Log.d("Home", "ERROR")
                }
                Status.LOADING -> {
                    Log.d("Home", "Loading")
                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when(event){
                    is AnimeEvent.NavigateToDetailScreen -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(event.anime)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_tab_bar, menu)

    }

    override fun itemClicked(anime: Anime) {
        viewModel.animeTapped(anime)
    }




}