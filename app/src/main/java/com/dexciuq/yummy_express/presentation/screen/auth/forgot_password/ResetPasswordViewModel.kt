package com.dexciuq.yummy_express.presentation.screen.auth.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.model.ResetPasswordConfig
import com.dexciuq.yummy_express.domain.use_case.auth.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : ViewModel() {

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    fun resetPassword(code: String, password: String) = viewModelScope.launch {
        _isSuccessful.postValue(
            resetPasswordUseCase.invoke(
                ResetPasswordConfig(
                    code = code,
                    password = password,
                )
            )
        )
    }
}