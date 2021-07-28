package com.alecbrando.animeapp.data.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class QueriedAnime(
    val results: List<Anime>
)