package com.example.mypet.ui.auth.data.model

import com.example.mypet.ui.auth.ui.AuthResult

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun logout() {}
}