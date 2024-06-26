package com.example.evaluacion_6.data.remote.response.Transaccion

data class AllTransaccionResponse(
    val previousPage: Any,
    val nextPage: Any,
    val `data`: List<Data>
)