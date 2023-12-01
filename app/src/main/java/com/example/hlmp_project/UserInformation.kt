package com.example.hlmp_project

class UserInformation private constructor() {
    private var userEmail: String? = null

    fun setUserEmail(email: String) {
        this.userEmail = email
    }

    fun getUserEmail(): String? {
        return userEmail
    }

    companion object {
        private val instance = UserInformation()

        fun getInstance(): UserInformation {
            return instance
        }
    }
}