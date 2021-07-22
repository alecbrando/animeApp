package com.alecbrando.animeapp.data.api.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Anime(
    val image_url: String,
    val mal_id: Int,
    val title: String,
) : Parcelable