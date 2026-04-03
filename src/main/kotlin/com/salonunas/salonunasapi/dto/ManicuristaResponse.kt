package com.salonunas.salonunasapi.dto

import java.time.LocalDateTime

data class ManicuristaResponse(
    val id: Long,
    val nombre: String,
    val telefono: String,
    val especialidad: String,
    val fechaRegistro: LocalDateTime
)
