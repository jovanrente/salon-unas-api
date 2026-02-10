package com.salonunas.salonunasapi.dto

import java.time.LocalDateTime

data class ClienteResponse(
    val id: Long,
    val nombre: String,
    val telefono: String,
    val fechaRegistro: LocalDateTime
)
