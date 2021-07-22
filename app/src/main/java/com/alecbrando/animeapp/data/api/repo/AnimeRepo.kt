package com.alecbrando.animeapp.data.api.repo

import com.alecbrando.animeapp.data.api.remote.ApiHelper
import javax.inject.Inject

class AnimeRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getTopAnime(sort_type: String) = apiHelper.getTopAnime(sort_type)
    suspend fun getAnimeById(mal_id : Int) = apiHelper.getAnimeById(mal_id)
}