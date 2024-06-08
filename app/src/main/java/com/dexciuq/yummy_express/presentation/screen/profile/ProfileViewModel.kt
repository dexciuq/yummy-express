package com.dexciuq.yummy_express.presentation.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.model.User
import com.dexciuq.yummy_express.domain.use_case.auth.GetAccessTokenUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.GetProfileInfoUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.LogoutUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.SetAccessTokenUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.SetAuthSkipUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.SetRefreshTokenUseCase
import com.dexciuq.yummy_express.domain.use_case.auth.UpdateProfileInfoUseCase
import com.dexciuq.yummy_express.domain.use_case.product.RemoveAllProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val updateProfileInfoUseCase: UpdateProfileInfoUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val removeAllProductFromCartUseCase: RemoveAllProductFromCartUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
    private val setAuthSkipUseCase: SetAuthSkipUseCase,
) : ViewModel() {

    private val _logoutCompleted = MutableLiveData<Boolean>()
    val logoutCompleted: LiveData<Boolean> = _logoutCompleted

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        val accessToken = getAccessTokenUseCase()
        if (accessToken.isNotBlank()) {
            val user = getProfileInfoUseCase(accessToken)
            _user.postValue(user)
        }
    }

    fun updateUser(userInfo: User) = viewModelScope.launch {
        _user.value = updateProfileInfoUseCase(userInfo)
    }

    fun logout() = viewModelScope.launch {
        val accessToken = getAccessTokenUseCase()
        logoutUseCase(accessToken)
        removeAllProductFromCartUseCase()
        setAccessTokenUseCase("")
        setRefreshTokenUseCase("")
        setAuthSkipUseCase(false)
        _logoutCompleted.postValue(true)
        _user.postValue(null)
    }
}