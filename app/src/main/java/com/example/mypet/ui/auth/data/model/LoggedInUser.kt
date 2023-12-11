package com.example.mypet.ui.auth.data.model

abstract class LoggedInUser {

    abstract val email: String
    abstract val id: String

    class Base(override val email: String, override val id: String) : LoggedInUser()
}