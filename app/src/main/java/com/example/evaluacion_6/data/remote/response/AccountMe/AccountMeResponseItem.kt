package com.example.evaluacion_6.data.remote.response.AccountMe

data class AccountMeResponseItem(
    val id: Int,
    val creationDate: String,
    val money: String,
    val isBlocked: Boolean,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String
)