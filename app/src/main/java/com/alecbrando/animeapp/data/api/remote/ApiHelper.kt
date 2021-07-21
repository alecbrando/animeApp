package com.alecbrando.animeapp.data.api.remote

import com.alecbrando.animeapp.data.api.models.TopAnime
import retrofit2.Response

interface ApiHelper {
    suspend fun getTopAnime() : Response<TopAnime>
}