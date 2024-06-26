package com.example.evaluacion_6.data.remote.response.Accounts

data class DataAccount(
    val id: Int,
    val creationDate: String,
    val money: String,
    val isBlocked: Boolean,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String
)