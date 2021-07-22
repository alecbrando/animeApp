package com.alecbrando.animeapp.data.api.repo

import com.alecbrando.animeapp.data.api.remote.ApiHelper
import javax.inject.Inject

class AnimeRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getTopAnime() = apiHelper.getTopAnime()
    suspend fun getAnimeById(mal_id : Int) = apiHelper.getAnimeById(mal_id)
}