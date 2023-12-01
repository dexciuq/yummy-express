package com.dexciuq.yummy_express.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.use_case.product.GetCartItemCountUseCase
import com.dexciuq.yummy_express.domain.use_case.ui.language.GetCurrentLanguageCodeUseCase
import com.dexciuq.yummy_express.domain.use_case.ui.theme.GetDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeActivityViewModel @Inject constructor(
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase
) : ViewModel() {

    private val _darkMode = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val darkMode get() = _darkMode.asStateFlow()

    private val _currentLanguageCode = MutableStateFlow<Resource<String>>(Resource.Loading)
    val currentLanguageCode get() = _currentLanguageCode.asStateFlow()

    init {
        getDarkMode()
        getCurrentLanguageCode()
    }

    private fun getDarkMode() = viewModelScope.launch {
        getDarkModeUseCase().collectLatest {
            _darkMode.emit(it)
        }
    }

    private fun getCurrentLanguageCode() = viewModelScope.launch {
        getCurrentLanguageCodeUseCase().collectLatest {
            _currentLanguageCode.emit(it)
        }
    }
}