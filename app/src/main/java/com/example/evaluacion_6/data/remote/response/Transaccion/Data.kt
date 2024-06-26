package com.example.evaluacion_6.data.remote.response.Transaccion

data class Data(
    val id: Int,
    val amount: String,
    val concept: String,
    val date: String,
    val type: String,
    val accountId: Int,
    val userId: Int,
    val to_account_id: Int,
    val createdAt: String,
    val updatedAt: String
)