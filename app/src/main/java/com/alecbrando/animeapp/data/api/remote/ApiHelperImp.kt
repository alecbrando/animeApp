package com.alecbrando.animeapp.data.api.remote

import com.alecbrando.animeapp.data.api.models.TopAnime
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImp @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getTopAnime(): Response<TopAnime> = apiService.getTopAnime()

}