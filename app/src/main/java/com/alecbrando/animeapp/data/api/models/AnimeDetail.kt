package com.alecbrando.animeapp.data.api.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class AnimeDetail(
    val title: String,
    val image_url: String,
    val mal_id: Int,
    val score: Double,
    val rank: Int,
    val popularity: Int,
    val members: Int,
    val synopsis: String
) : Parcelable