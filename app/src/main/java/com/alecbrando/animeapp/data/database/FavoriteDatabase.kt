package com.alecbrando.animeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alecbrando.animeapp.data.models.AnimeDetail

@Database(entities=[AnimeDetail::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun AnimeDao () : AnimeDao

}