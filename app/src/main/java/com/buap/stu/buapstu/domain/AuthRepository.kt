package com.buap.stu.buapstu.domain

import com.buap.stu.buapstu.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val user: Flow<User>
    suspend fun logOut()
    suspend fun signInWithEmailAndPassword(email:String, password:String):String
}