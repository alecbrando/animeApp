package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.animeapp.data.database.AnimeDao
import com.alecbrando.animeapp.data.models.AnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val animeDao: AnimeDao,
    private val state: SavedStateHandle
) : ViewModel() {

    init {
        getFavoriteAnime()
    }

    private val anime = state.get<AnimeDetail>("AnimeDetail")
    private val animeList = MutableLiveData<List<AnimeDetail>>()
    private val favoriteEvents = Channel<FavoriteEvent>()

    val flowFavoriteEvents = favoriteEvents.receiveAsFlow()

    val favoriteAnime get() = animeList
    val getAnime get() = anime


    private fun getFavoriteAnime() = viewModelScope.launch {

        animeDao.getFavoriteAnime().collect {
            animeList.value = it
        }
    }

    fun removeAnimeFromFavorites() = viewModelScope.launch {
        animeDao.removeFavoriteAnime(anime!!)
        favoriteEvents.send(FavoriteEvent.NavigateBackToFavoriteWithMessage(anime))
    }


}


sealed class FavoriteEvent {
    data class NavigateBackToFavoriteWithMessage(val anime: AnimeDetail) : FavoriteEvent()
}