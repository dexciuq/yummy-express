package com.dexciuq.yummy_express.presentation.screen.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexciuq.yummy_express.domain.use_case.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registered = MutableLiveData<Boolean>()
    val registered: LiveData<Boolean> = _registered

    fun register(name: String, surname: String, email: String, phone: String, password: String) =
        viewModelScope.launch {
            val valid = registerUseCase(name, surname, email, phone, password)
            _registered.postValue(valid)
        }
}