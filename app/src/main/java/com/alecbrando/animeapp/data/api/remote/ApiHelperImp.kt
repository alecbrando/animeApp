package com.alecbrando.animeapp.data.api.remote

import com.alecbrando.animeapp.data.api.models.AnimeDetail
import com.alecbrando.animeapp.data.api.models.TopAnime
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImp @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getTopAnime(sort_type: String): Response<TopAnime> = apiService.getTopAnime(sort_type)
    override suspend fun getAnimeById(mal_id: Int): Response<AnimeDetail> = apiService.getAnimeById(mal_id)

}