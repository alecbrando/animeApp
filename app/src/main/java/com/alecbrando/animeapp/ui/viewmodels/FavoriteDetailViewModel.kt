package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.animeapp.data.database.AnimeDao
import com.alecbrando.animeapp.data.models.AnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDetailViewModel @Inject constructor(
    private val animeDao: AnimeDao,
    private val state: SavedStateHandle
) : ViewModel() {

    private val anime = state.get<AnimeDetail>("AnimeDetail")

    fun removeAnimeFromFavorites() = viewModelScope.launch {
        animeDao.removeFavoriteAnime(anime!!)
    }


    val getAnime get() = anime

}