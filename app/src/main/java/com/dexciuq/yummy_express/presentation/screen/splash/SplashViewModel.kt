package com.dexciuq.yummy_express.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.use_case.ui.on_boarding.GetOnBoardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnBoardingCompleteUseCase: GetOnBoardingCompleteUseCase,
) : ViewModel() {

    private val _isCompleted = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val isCompleted get() = _isCompleted.asStateFlow()

    init {
        getOnBoardingComplete()
    }

    private fun getOnBoardingComplete() = viewModelScope.launch {
        getOnBoardingCompleteUseCase().collectLatest {
            _isCompleted.emit(it)
        }
    }
}