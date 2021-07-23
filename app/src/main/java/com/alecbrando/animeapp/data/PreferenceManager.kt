package com.alecbrando.animeapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


enum class SortBy(val value : String) {
    BY_POPULARITY("bypopularity"),
    UPCOMING("upcoming"),
    MOVIES("movie")
}

data class FilterPreferences(val sortBy: SortBy)

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val datastore = context.dataStore

    val preferencesFlow = datastore.data.map { preferences ->
        val sortBy = preferences[PreferenceKeys.SORT_BY]?.let { SortBy.valueOf(it) } ?: SortBy.BY_POPULARITY
        FilterPreferences(sortBy)
    }

    suspend fun updateSortBy(sortBy: SortBy) {
        datastore.edit {
            it[PreferenceKeys.SORT_BY] = sortBy.name
        }
    }

    private object PreferenceKeys {
        val SORT_BY = stringPreferencesKey("sort_by")
    }
}

