package com.buap.stu.buapstu.data.remote.auth

interface AuthDataSource {
    suspend fun signInWithEmailAndPassword(email:String, password:String):String
    suspend fun logOut()
}