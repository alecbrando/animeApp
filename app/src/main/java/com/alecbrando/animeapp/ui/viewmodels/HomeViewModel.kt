package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.animeapp.data.api.models.Anime
import com.alecbrando.animeapp.data.api.models.TopAnime
import com.alecbrando.animeapp.data.api.repo.AnimeRepo
import com.alecbrando.animeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AnimeRepo,
) : ViewModel() {
    private var sort_type: String = "bypopularity"
    private val _res = MutableLiveData<Resource<TopAnime>>(Resource.loading(null))
    private val animeEvent = Channel<AnimeEvent>()

    val event = animeEvent.receiveAsFlow()

    val res: LiveData<Resource<TopAnime>>
        get() = _res

    init {
        getTopAnime(sort_type)
    }

    private fun getTopAnime(sort_type: String) = viewModelScope.launch {
            repo.getTopAnime(sort_type).let {
                if(it.isSuccessful){
                    _res.postValue(Resource.success(it.body()))
                } else {
                    _res.postValue(Resource.error(it.errorBody().toString(), null))
                }
        }
    }

    fun animeTapped(anime: Anime) = viewModelScope.launch {
        animeEvent.send(AnimeEvent.NavigateToDetailScreen(anime))
    }

    fun sortByItemSelected(sortBy: SortBy) {
        when (sortBy) {
            SortBy.BY_POPULARITY -> {
                sort_type = "bypopularity"
                getTopAnime(sort_type)
            }
            SortBy.UPCOMING -> {
                sort_type = "upcoming"
                getTopAnime(sort_type)
            }
            SortBy.MOVIES -> {
                sort_type = "movie"
                getTopAnime(sort_type)
            }
        }
    }


}

sealed class AnimeEvent {
    data class NavigateToDetailScreen(val anime : Anime) : AnimeEvent()
}

enum class SortBy {
    BY_POPULARITY,
    UPCOMING,
    MOVIES
}