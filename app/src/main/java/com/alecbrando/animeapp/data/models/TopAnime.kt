package com.alecbrando.animeapp.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopAnime(
    val top: List<Anime>
)