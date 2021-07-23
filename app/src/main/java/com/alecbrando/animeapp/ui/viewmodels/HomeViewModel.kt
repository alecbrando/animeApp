package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.*
import com.alecbrando.animeapp.data.PreferenceManager
import com.alecbrando.animeapp.data.SortBy
import com.alecbrando.animeapp.data.api.models.Anime
import com.alecbrando.animeapp.data.api.repo.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AnimeRepo,
    private val state: SavedStateHandle,
    private val preferencesManager : PreferenceManager
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")

    private val preferenceFlow = preferencesManager.preferencesFlow


//    private var sort_type = flowOf("bypopularity")
    private val animeEvent = Channel<AnimeEvent>()

    val event = animeEvent.receiveAsFlow()

//    private val _res = MutableLiveData<Resource<TopAnime>>(Resource.loading(null))
//    val res: LiveData<Resource<TopAnime>>
//        get() = _res

//    init {
//        getTopAnime(sort_type)
//    }

    private val animeFlow = combine(
        searchQuery.asFlow(),
        preferenceFlow,
    ) { query, filter ->
        Pair(query, filter)
    }.flatMapLatest { (searchQuery, preferences) ->
        if (searchQuery?.length!! > 3) {
            flow{
            emit(repo.getQueriedAnime(searchQuery).body()!!.results)
            }
        } else {
        flow {
           emit(repo.getTopAnime(preferences.sortBy.value).body()!!.top)
        }

        }
    }

    val animeData = animeFlow.asLiveData()

//    private fun getTopAnime(sort_type: String) = viewModelScope.launch {
//            repo.getTopAnime(sort_type).let {
//                if (it.isSuccessful) {
//                    _res.postValue(Resource.success(it.body()))
//                } else {
//                    _res.postValue(Resource.error(it.errorBody().toString(), null))
//                }
//            }
//    }

    fun animeTapped(anime: Anime) = viewModelScope.launch {
        animeEvent.send(AnimeEvent.NavigateToDetailScreen(anime))
    }

    fun onSortedOrderSelected(sortOrder: SortBy) = viewModelScope.launch {
        preferencesManager.updateSortBy(sortOrder)
    }

    sealed class AnimeEvent {
        data class NavigateToDetailScreen(val anime: Anime) : AnimeEvent()
    }
}

