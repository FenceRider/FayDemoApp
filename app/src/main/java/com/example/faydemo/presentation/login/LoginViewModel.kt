package com.example.faydemo.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class LoginState(
    val initialized: Boolean = false,
    val phoneNumber: String = "",
)

sealed class LoginNavEvent {
    data object SuccessfulLogin : LoginNavEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    //login repo
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<LoginNavEvent>()
    val navEvent = _navEvent.asSharedFlow()


    fun init() {}

    fun login() {
        //val username = uiState.value.phoneNumber

        //login to api

        viewModelScope.launch {
            _navEvent.emit(LoginNavEvent.SuccessfulLogin)
        }
    }

    fun setUsername(newValue: String) {
        _uiState.update { it.copy(phoneNumber = newValue) }
    }

}