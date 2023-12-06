package com.dexciuq.yummy_express.presentation.screen.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.use_case.auth.GetAuthSkipUseCase
import com.dexciuq.yummy_express.domain.use_case.ui.GetOnBoardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnBoardingCompleteUseCase: GetOnBoardingCompleteUseCase,
    private val getAuthSkipUseCase: GetAuthSkipUseCase,
) : ViewModel() {

    private val _isCompleted = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val isCompleted get() = _isCompleted.asStateFlow()

    private val _authSkip = MutableLiveData<Boolean>()
    val authSkip: LiveData<Boolean> = _authSkip

    init {
        getOnBoardingComplete()
    }

    fun getAuthSkip() = viewModelScope.launch {
        _authSkip.postValue(
            getAuthSkipUseCase.invoke()
        )
    }

    private fun getOnBoardingComplete() = viewModelScope.launch {
        getOnBoardingCompleteUseCase().collectLatest {
            _isCompleted.emit(it)
        }
    }
}