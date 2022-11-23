package com.buap.stu.buapstu.core.states

import com.buap.stu.buapstu.models.User

sealed class AuthState{
    object Authenticating : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val user: User):AuthState()
}
