package com.example.mypet.ui.auth.data

import com.example.mypet.ui.auth.data.model.AuthRepository
import com.example.mypet.ui.auth.data.model.LoggedInUser
import com.example.mypet.ui.auth.ui.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(LoggedInUser.Base(user.email ?: " ", user.uid))
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(LoggedInUser.Base(user.email ?: " ", user.uid))
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun logoutCurrentUser(): AuthResult {
        return try {
            auth.signOut()
            AuthResult.SuccessOut(LoggedInUser.Empty)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

}