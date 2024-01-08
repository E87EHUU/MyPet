package com.example.mypet.ui.auth.data.model

import com.example.mypet.ui.auth.ui.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun logoutCurrentUser(): AuthResult

    fun isLoggedIn(): Boolean
}