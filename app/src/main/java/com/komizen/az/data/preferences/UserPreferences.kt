package com.komizen.az.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    private val themeKey = stringPreferencesKey("theme")
    private val languageKey = stringPreferencesKey("language")
    private val notificationsKey = booleanPreferencesKey("notifications")
    private val autoSyncKey = booleanPreferencesKey("auto_sync")
    private val syncIntervalKey = intPreferencesKey("sync_interval")
    private val lastSyncTimeKey = longPreferencesKey("last_sync_time")

    val theme: Flow<String> = context.dataStore.data.map { it[themeKey] ?: "system" }
    val language: Flow<String> = context.dataStore.data.map { it[languageKey] ?: "en" }
    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { it[notificationsKey] ?: true }
    val autoSyncEnabled: Flow<Boolean> = context.dataStore.data.map { it[autoSyncKey] ?: true }
    val syncIntervalMinutes: Flow<Int> = context.dataStore.data.map { it[syncIntervalKey] ?: 360 }
    val lastSyncTime: Flow<Long> = context.dataStore.data.map { it[lastSyncTimeKey] ?: 0L }

    suspend fun updateTheme(theme: String) {
        context.dataStore.edit { it[themeKey] = theme }
    }

    suspend fun updateLanguage(language: String) {
        context.dataStore.edit { it[languageKey] = language }
    }

    suspend fun updateNotifications(enabled: Boolean) {
        context.dataStore.edit { it[notificationsKey] = enabled }
    }

    suspend fun updateAutoSync(enabled: Boolean) {
        context.dataStore.edit { it[autoSyncKey] = enabled }
    }

    suspend fun updateSyncInterval(minutes: Int) {
        context.dataStore.edit { it[syncIntervalKey] = minutes }
    }

    suspend fun updateLastSyncTime(time: Long) {
        context.dataStore.edit { it[lastSyncTimeKey] = time }
    }
}