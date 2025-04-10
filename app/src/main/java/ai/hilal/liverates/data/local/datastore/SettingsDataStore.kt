package ai.hilal.liverates.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface SettingsDataStore {
    val isDarkTheme: Flow<Boolean>

    suspend fun setDarkTheme(isDarkTheme: Boolean)
}