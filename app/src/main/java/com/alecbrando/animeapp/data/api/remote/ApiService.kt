package com.alecbrando.animeapp.data.api.remote

import com.alecbrando.animeapp.data.api.models.AnimeDetail
import com.alecbrando.animeapp.data.api.models.TopAnime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("top/anime/1/{sort_type}")
    suspend fun getTopAnime(@Path("sort_type") sort_type : String) : Response<TopAnime>

    @GET("anime/{mal_id}")
    suspend fun getAnimeById(@Path("mal_id") mal_id : Int): Response<AnimeDetail>
}