package com.alecbrando.animeapp.ui.fragments.Home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.alecbrando.animeapp.R
import com.alecbrando.animeapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_tab_bar, menu)

    }

}