package com.example.mypet.ui.auth.ui

import com.example.mypet.ui.auth.data.model.LoggedInUser

sealed class AuthResult {
    data class Success(val user: LoggedInUser) : AuthResult()
    data class Error(val exception: Exception) : AuthResult()
    object Loading : AuthResult()
}