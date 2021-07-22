package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.*
import com.alecbrando.animeapp.data.api.models.Anime
import com.alecbrando.animeapp.data.api.models.AnimeDetail
import com.alecbrando.animeapp.data.api.repo.AnimeRepo
import com.alecbrando.animeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: AnimeRepo,
    private val state: SavedStateHandle
) : ViewModel() {
    private val anime = state.get<Anime>("Anime")

    private val _res = MutableLiveData<Resource<AnimeDetail>>()

    val res: LiveData<Resource<AnimeDetail>>
        get() = _res

    init {
        getAnimeById()
    }

    private fun getAnimeById() = viewModelScope.launch {
        repo.getAnimeById(anime!!.mal_id).let {
            if (it.isSuccessful) {
                _res.postValue(Resource.success(it.body()))
            } else {
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

}