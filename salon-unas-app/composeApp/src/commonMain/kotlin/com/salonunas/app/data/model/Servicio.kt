package com.salonunas.app.data.model

import kotlinx.serialization.Serializable

enum class TipoServicio {
    PERMANENTE,
    SEMI_PERMANENTE,
    ARREGLO_UNAS;

    fun etiqueta(): String = when (this) {
        PERMANENTE -> "Permanente"
        SEMI_PERMANENTE -> "Semi-permanente"
        ARREGLO_UNAS -> "Arreglo de uñas"
    }
}

enum class AplicaPara {
    MANOS,
    PIES,
    MANOS_Y_PIES;

    fun etiqueta(): String = when (this) {
        MANOS -> "Manos"
        PIES -> "Pies"
        MANOS_Y_PIES -> "Manos y pies"
    }
}

@Serializable
data class Servicio(
    val id: Long,
    val nombre: String,
    val tipo: TipoServicio,
    val aplicaPara: AplicaPara,
    val descripcion: String? = null,
    val precio: Double,
    val activo: Boolean,
    val fechaCreacion: String
)

@Serializable
data class ServicioRequest(
    val nombre: String,
    val tipo: TipoServicio,
    val aplicaPara: AplicaPara,
    val descripcion: String? = null,
    val precio: Double,
    val activo: Boolean = true
)
