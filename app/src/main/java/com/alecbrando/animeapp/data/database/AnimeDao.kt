package com.alecbrando.animeapp.data.database

import androidx.room.*
import com.alecbrando.animeapp.data.models.AnimeDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun favoriteAnime(anime: AnimeDetail)

    @Delete
    suspend fun removeFavoriteAnime(anime: AnimeDetail)

    @Query("SELECT * FROM favorite_anime")
    fun getFavoriteAnime() : Flow<List<AnimeDetail>>

    @Query("SELECT * FROM favorite_anime WHERE mal_id IN (:id)")
    fun getFavoriteAnimeById(id: Int) : Flow<AnimeDetail>

}