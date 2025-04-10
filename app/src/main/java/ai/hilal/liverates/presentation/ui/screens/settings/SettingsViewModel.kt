package ai.hilal.liverates.presentation.ui.screens.settings

import ai.hilal.liverates.data.local.datastore.SettingsDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.isDarkTheme.collect { isDarkTheme ->
                _uiState.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }

    }

    fun toggleDarkTheme() {
        viewModelScope.launch {
            dataStore.setDarkTheme(!_uiState.value.isDarkTheme)
        }
    }

} 