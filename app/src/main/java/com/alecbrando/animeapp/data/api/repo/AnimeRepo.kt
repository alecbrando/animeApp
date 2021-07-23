package com.alecbrando.animeapp.data.api.repo

import com.alecbrando.animeapp.data.api.models.TopAnime
import com.alecbrando.animeapp.data.api.remote.ApiHelperImp
import retrofit2.Response
import javax.inject.Inject

class AnimeRepo @Inject constructor(private val apiHelper: ApiHelperImp) {

     suspend fun getTopAnime(sort_type: String) : Response<TopAnime> = apiHelper.getTopAnime(sort_type)

     suspend fun getAnimeById(mal_id : Int) = apiHelper.getAnimeById(mal_id)

     suspend fun getQueriedAnime(query: String) = apiHelper.getQueriedAnime(query)

}