package com.dexciuq.yummy_express.presentation.screen.on_boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.OnBoarding
import com.dexciuq.yummy_express.domain.use_case.ui.on_boarding.GetOnBoardingItemsUseCase
import com.dexciuq.yummy_express.domain.use_case.ui.on_boarding.SetOnBoardingCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getOnBoardingItemsUseCase: GetOnBoardingItemsUseCase,
    private val setOnBoardingCompleteUseCase: SetOnBoardingCompleteUseCase
) : ViewModel() {

    private val _onBoardingItems = MutableStateFlow<Resource<List<OnBoarding>>>(Resource.Loading)
    val onBoardingItems get() = _onBoardingItems.asStateFlow()

    init {
        getOnBoardingItems()
    }

    private fun getOnBoardingItems() = viewModelScope.launch {
        getOnBoardingItemsUseCase().collectLatest {
            _onBoardingItems.emit(it)
        }
    }

    fun setOnBoardingComplete() = viewModelScope.launch {
        setOnBoardingCompleteUseCase(true)
    }
}