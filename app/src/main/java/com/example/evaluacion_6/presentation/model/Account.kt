package com.example.evaluacion_6.presentation.model

data class Account(
    val creationDate: String,
    val money: Int,
    val isBlocked: Boolean,
    val userId: Long?
)