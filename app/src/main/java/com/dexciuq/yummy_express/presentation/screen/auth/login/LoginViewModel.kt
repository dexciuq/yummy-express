package com.dexciuq.yummy_express.presentation.screen.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.use_case.auth.LoginUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.SetAccessTokenUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.SetAuthSkipUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.SetRefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
    private val setAuthSkipUseCase: SetAuthSkipUseCase,
) : ViewModel() {

    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    fun login(email: String, password: String) = viewModelScope.launch {
        val authTokens = loginUseCase(email, password)
        if (authTokens == null) {
            _login.postValue(false)
            return@launch
        }
        setAuthSkipUseCase(true)
        setAccessTokenUseCase(authTokens.accessToken)
        setRefreshTokenUseCase(authTokens.refreshToken)
        _login.postValue(true)
    }
}