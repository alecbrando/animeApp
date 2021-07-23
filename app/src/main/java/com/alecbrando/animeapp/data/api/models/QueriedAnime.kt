package com.alecbrando.animeapp.data.api.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class QueriedAnime(
    val results: List<Anime>
)