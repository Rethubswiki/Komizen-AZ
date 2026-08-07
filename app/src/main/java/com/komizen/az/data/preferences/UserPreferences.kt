package com.komizen.az.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.komizen.az.data.model.RepoConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "komizen_prefs")

class UserPreferences(private val context: Context) {

    private val gson = Gson()

    companion object {
        val AUTO_UPDATE = booleanPreferencesKey("auto_update")
        val DARK_MODE = intPreferencesKey("dark_mode")
        val REPOS = stringPreferencesKey("repos")
    }

    val autoUpdate: Flow<Boolean> = context.dataStore.data
        .map { it[AUTO_UPDATE] ?: false }

    val darkMode: Flow<Int> = context.dataStore.data
        .map { it[DARK_MODE] ?: 0 }

    val repos: Flow<List<RepoConfig>> = context.dataStore.data
        .map { prefs ->
            val json = prefs[REPOS] ?: "[]"
            val type = object : TypeToken<List<RepoConfig>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        }

    suspend fun setAutoUpdate(enabled: Boolean) {
        context.dataStore.edit { it[AUTO_UPDATE] = enabled }
    }

    suspend fun setDarkMode(mode: Int) {
        context.dataStore.edit { it[DARK_MODE] = mode }
    }

    suspend fun setRepos(repos: List<RepoConfig>) {
        context.dataStore.edit { it[REPOS] = gson.toJson(repos) }
    }
}
