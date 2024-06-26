package com.example.evaluacion_6.data.remote.response.Transaccion

data class TransaccionResponse(
    val id: Int,
    val amount: Int,
    val concept: String,
    val date: String,
    val type: String,
    val accountId: Int,
    val userId: Int,
    val to_account_id: Int,
    val updatedAt: String,
    val createdAt: String
)