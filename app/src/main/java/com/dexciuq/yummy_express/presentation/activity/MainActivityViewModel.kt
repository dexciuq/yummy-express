package com.dexciuq.yummy_express.presentation.activity

import androidx.lifecycle.ViewModel
import com.dexciuq.yummy_express.domain.use_case.product.GetCartItemCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCartItemCountUseCase: GetCartItemCountUseCase,
) : ViewModel() {

    val cartItemCount get() = getCartItemCountUseCase()
}