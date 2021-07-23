package com.alecbrando.animeapp.data.api.remote

import com.alecbrando.animeapp.data.api.models.AnimeDetail
import com.alecbrando.animeapp.data.api.models.QueriedAnime
import com.alecbrando.animeapp.data.api.models.TopAnime
import retrofit2.Response

interface ApiHelper {
    suspend fun getTopAnime(sort_type: String) : Response<TopAnime>
    suspend fun getAnimeById(mal_id : Int) : Response<AnimeDetail>
    suspend fun  getQueriedAnime(query: String) : Response<QueriedAnime>
}