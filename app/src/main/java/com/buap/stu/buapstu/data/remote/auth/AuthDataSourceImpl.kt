package com.buap.stu.buapstu.data.remote.auth

import com.buap.stu.buapstu.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthDataSourceImpl: AuthDataSource {

    private val auth= FirebaseAuth.getInstance()

    override suspend fun signInWithEmailAndPassword(email: String, password: String): String {
        val authReponse=auth.signInWithEmailAndPassword(email, password).await()
        return authReponse.user!!.uid
    }

    override suspend fun logOut() {
        auth.signOut()
    }

    override suspend fun signUpUser(newUser: User):String {
        val response=auth.createUserWithEmailAndPassword(newUser.correo,newUser.contrasena).await()
        return response.user?.uid.toString()
    }
}