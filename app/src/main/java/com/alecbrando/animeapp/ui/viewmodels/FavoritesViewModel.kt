package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.animeapp.data.database.AnimeDao
import com.alecbrando.animeapp.data.models.AnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val animeDao: AnimeDao
) : ViewModel() {

    private val anime = MutableLiveData<List<AnimeDetail>>()

    val favoriteAnime get() = anime

    init {
        getFavoriteAnime()
    }

   private fun getFavoriteAnime() = viewModelScope.launch {

      animeDao.getFavoriteAnime().collect {
            anime.value = it
      }
    }



}