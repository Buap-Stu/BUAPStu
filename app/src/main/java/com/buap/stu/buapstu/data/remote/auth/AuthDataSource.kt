package com.buap.stu.buapstu.data.remote.auth

import com.buap.stu.buapstu.models.User

interface AuthDataSource {
    suspend fun signInWithEmailAndPassword(email:String, password:String):String
    suspend fun logOut()
    suspend fun signUpUser(newUser: User):String
}