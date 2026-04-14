package com.salonunas.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Manicurista(
    val id: Long,
    val nombre: String,
    val telefono: String,
    val especialidad: String,
    val fechaRegistro: String
)

@Serializable
data class ManicuristaRequest(
    val nombre: String,
    val telefono: String,
    val especialidad: String
)
