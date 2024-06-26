package com.example.evaluacion_6.presentation.model

data class User(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val roleId: Int = 1,
    val points: Int = 0
)