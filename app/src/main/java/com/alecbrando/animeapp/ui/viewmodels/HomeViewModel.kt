package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.*
import com.alecbrando.animeapp.data.PreferenceManager
import com.alecbrando.animeapp.data.SortBy
import com.alecbrando.animeapp.data.api.repo.AnimeRepo
import com.alecbrando.animeapp.data.models.Anime
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


    private val animeEvent = Channel<AnimeEvent>()

    val event = animeEvent.receiveAsFlow()


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

