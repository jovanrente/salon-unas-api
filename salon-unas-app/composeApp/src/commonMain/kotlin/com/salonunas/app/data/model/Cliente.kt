package com.salonunas.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val id: Long,
    val nombre: String,
    val telefono: String,
    val fechaRegistro: String
)

@Serializable
data class ClienteRequest(
    val nombre: String,
    val telefono: String
)
