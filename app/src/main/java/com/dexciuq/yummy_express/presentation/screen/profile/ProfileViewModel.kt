package com.dexciuq.yummy_express.presentation.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.use_case.ui.theme.GetDarkModeUseCase
import com.dexciuq.yummy_express.domain.use_case.ui.theme.SetDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
) : ViewModel() {

    private val _darkMode = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val darkMode get() = _darkMode.asStateFlow()

    init {
        getDarkMode()
    }

    fun setDarkMode(isDarkMode: Boolean) = viewModelScope.launch {
        setDarkModeUseCase(isDarkMode)
    }

    private fun getDarkMode() = viewModelScope.launch {
        getDarkModeUseCase().collectLatest {
            _darkMode.emit(it)
        }
    }
}