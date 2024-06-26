package com.example.evaluacion_6.data.remote.response.AccountMe

data class AccountResponse(
    val id: Int,
    val creationDate: String,
    val money: Int,
    val isBlocked: Boolean,
    val userId: Int,
    val updatedAt: String,
    val createdAt: String
)