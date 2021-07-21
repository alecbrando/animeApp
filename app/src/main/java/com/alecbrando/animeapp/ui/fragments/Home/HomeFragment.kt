package com.alecbrando.animeapp.ui.fragments.Home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alecbrando.animeapp.R
import com.alecbrando.animeapp.databinding.FragmentHomeBinding
import com.alecbrando.animeapp.ui.viewmodels.ApiViewModel
import com.alecbrando.animeapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : ApiViewModel by viewModels()

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.res.observe(viewLifecycleOwner){ it ->
            when(it.status){
                Status.SUCCESS -> {
                    Log.d("Home", it.data!!.top[0].title)
                }
                Status.ERROR -> {
                    Log.d("Home", "ERROR")
                }
                Status.LOADING -> {
                    Log.d("Home", "Loading")
                }
            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_tab_bar, menu)

    }

}