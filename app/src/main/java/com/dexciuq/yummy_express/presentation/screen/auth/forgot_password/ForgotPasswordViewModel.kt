package com.dexciuq.yummy_express.presentation.screen.auth.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.use_case.auth.SendCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val sendCodeUseCase: SendCodeUseCase,
) : ViewModel() {

    private val _isSend = MutableLiveData<Boolean>()
    val isSend: LiveData<Boolean> = _isSend

    fun sendCode(email: String) = viewModelScope.launch {
        _isSend.postValue(sendCodeUseCase.invoke(email))
    }
}