package com.alecbrando.animeapp.data.api.remote

import com.alecbrando.animeapp.data.api.models.TopAnime
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("top/anime/1/bypopularity")
    suspend fun getTopAnime() : Response<TopAnime>

}