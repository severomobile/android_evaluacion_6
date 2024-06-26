package com.example.evaluacion_6.presentation.model

data class Transaccion(
    val amount: Int,
    val concept: String,
    val date: String,
    val type: String,
    val accountId: Long?,
    val userId: Long?,
    val to_account_id: Long?
)