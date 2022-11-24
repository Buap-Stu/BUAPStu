package com.buap.stu.buapstu.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buap.stu.buapstu.core.states.AuthState
import com.buap.stu.buapstu.domain.AuthRepository
import com.buap.stu.buapstu.domain.database.DatabaseRepository
import com.buap.stu.buapstu.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val database: DatabaseRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _messageAuth = Channel<String>()
    val messageAuth = _messageAuth.receiveAsFlow()

    val userState = authRepository.user.transform { user ->
        when {
            user.uid.isEmpty() -> emit(AuthState.Unauthenticated)
            else -> emit(AuthState.Authenticated(user))
        }
    }.catch {
        emit(AuthState.Unauthenticated)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AuthState.Authenticating
    )


    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callBackSuccess: (String) -> Unit
    ) = viewModelScope.launch {
            _isLoading.value = true
            try {
                val type=authRepository.signInWithEmailAndPassword(email, password)
                callBackSuccess(type)
            } catch (e: Exception) {
                _messageAuth.trySend("Error en auth")
                Timber.e("error auth $e")
            }
            _isLoading.value = false
        }

    fun signOut() = viewModelScope.launch {
        authRepository.logOut()
    }

    fun addCredits() = viewModelScope.launch {
        database.addCredits()
    }

    fun signUp(newUser: User,callBackSuccess: () -> Unit) = viewModelScope.launch {
        _isLoading.value=true
        try {
            authRepository.signUpUser(newUser)
            callBackSuccess()
        }catch (e:Exception) {
            Timber.d("$e")
        }
        _isLoading.value=false

    }

}