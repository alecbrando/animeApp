package com.alecbrando.animeapp.data.api.repo

import com.alecbrando.animeapp.data.api.remote.ApiService
import com.alecbrando.animeapp.data.models.TopAnime
import retrofit2.Response
import javax.inject.Inject

class AnimeRepo @Inject constructor(private val apiService: ApiService) {

     suspend fun getTopAnime(sort_type: String) : Response<TopAnime> = apiService.getTopAnime(sort_type)

     suspend fun getAnimeById(mal_id : Int) = apiService.getAnimeById(mal_id)

     suspend fun getQueriedAnime(query: String) = apiService.getQueriedAnime(query)

}