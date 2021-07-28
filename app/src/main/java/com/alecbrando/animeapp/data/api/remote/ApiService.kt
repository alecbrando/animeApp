package com.alecbrando.animeapp.data.api.remote

import com.alecbrando.animeapp.data.models.AnimeDetail
import com.alecbrando.animeapp.data.models.QueriedAnime
import com.alecbrando.animeapp.data.models.TopAnime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/anime")
    suspend fun getQueriedAnime(@Query("q") query: String ) : Response<QueriedAnime>

    @GET("top/anime/1/{sort_type}")
    suspend fun getTopAnime(@Path("sort_type") sort_type : String) : Response<TopAnime>

    @GET("anime/{mal_id}")
     suspend fun getAnimeById(@Path("mal_id") mal_id : Int): Response<AnimeDetail>
}