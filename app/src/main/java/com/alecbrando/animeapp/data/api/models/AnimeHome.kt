package com.alecbrando.animeapp.data.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimeHome(
    val image_url: String,
    val mal_id: Int,
    val title: String,
)