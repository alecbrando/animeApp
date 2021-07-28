package com.alecbrando.animeapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "favorite_anime")
data class AnimeDetail(
    @PrimaryKey
    val mal_id: Int,
    val title: String,
    val image_url: String,
    val score: Double?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val synopsis: String
) : Parcelable