package com.alecbrando.animeapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.animeapp.data.api.models.TopAnime
import com.alecbrando.animeapp.data.api.repo.AnimeRepo
import com.alecbrando.animeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val repo: AnimeRepo
) : ViewModel() {

    private val _res = MutableLiveData<Resource<TopAnime>>(Resource.loading(null))

    val res: LiveData<Resource<TopAnime>>
        get() = _res

    init {
        getTopAnime()
    }

    private fun getTopAnime() = viewModelScope.launch {
//            emit(Resource.loading(null))
            repo.getTopAnime().let {
                if(it.isSuccessful){
                    _res.postValue(Resource.success(it.body()))
                } else {
                    _res.postValue(Resource.error(it.errorBody().toString(), null))
                }

        }
    }

}