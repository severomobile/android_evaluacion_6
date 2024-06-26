package com.example.evaluacion_6.data.remote.response.Accounts

data class AccountListResponse(
    val previousPage: Any,
    val nextPage: String,
    val `data`: List<DataAccount>
)