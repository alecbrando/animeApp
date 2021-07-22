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

    private val _res = MutableLiveData<Resource<TopAnime>>(Resource.loading(null))
    private val animeEvent = Channel<AnimeEvent>()

    val event = animeEvent.receiveAsFlow()

    val res: LiveData<Resource<TopAnime>>
        get() = _res

    init {
        getTopAnime()
    }

    private fun getTopAnime() = viewModelScope.launch {
            repo.getTopAnime().let {
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




}

sealed class AnimeEvent {
    data class NavigateToDetailScreen(val anime : Anime) : AnimeEvent()
}