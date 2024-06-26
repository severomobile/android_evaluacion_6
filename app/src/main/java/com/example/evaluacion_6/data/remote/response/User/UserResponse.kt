package com.example.evaluacion_6.data.remote.response.User

data class UserResponse(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val points: Int,
    val roleId: Int,
    val updatedAt: String,
    val createdAt: String
)