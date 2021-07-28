package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.*
import com.alecbrando.animeapp.data.api.repo.AnimeRepo
import com.alecbrando.animeapp.data.database.AnimeDao
import com.alecbrando.animeapp.data.models.Anime
import com.alecbrando.animeapp.data.models.AnimeDetail
import com.alecbrando.animeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: AnimeRepo,
    private val animeDao: AnimeDao,
    private val state: SavedStateHandle
) : ViewModel() {
    private val anime = state.get<Anime>("Anime")
    var favorited = false

    private val _res = MutableLiveData<Resource<AnimeDetail>>()
    private val detailsEvents = Channel<DetailEvents>()
    val detailFlowEvents = detailsEvents.receiveAsFlow()
    val res: LiveData<Resource<AnimeDetail>>
        get() = _res

    init {
        getAnimeById()
        checkIfAnimeIsAFavorite()
    }

    private fun checkIfAnimeIsAFavorite() = viewModelScope.launch {
        val res = animeDao.getFavoriteAnimeById(anime?.mal_id!!)
        if(res != null){
                favorited = true
        } else {
                favorited = false
        }

    }

    private fun getAnimeById() = viewModelScope.launch {
            _res.postValue(Resource.loading(null))
        repo.getAnimeById(anime!!.mal_id).let {
            if (it.isSuccessful) {
                _res.postValue(Resource.success(it.body()))
            } else {
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun addAnimeToFavorites() = viewModelScope.launch {
        animeDao.favoriteAnime(res.value?.data!!)
        detailsEvents.send(DetailEvents.AddedToFavorites(res.value?.data!!))
    }

    fun removeFromFavorites() = viewModelScope.launch {
        animeDao.removeFavoriteAnime(res.value?.data!!)
        detailsEvents.send(DetailEvents.RemovedFromFavorites(res.value?.data!!))
    }


}

sealed class DetailEvents {
    data class AddedToFavorites(val anime: AnimeDetail) : DetailEvents()
    data class RemovedFromFavorites(val anime: AnimeDetail) : DetailEvents()
}