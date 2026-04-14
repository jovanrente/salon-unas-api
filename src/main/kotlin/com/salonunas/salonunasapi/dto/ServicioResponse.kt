package com.salonunas.salonunasapi.dto

import com.salonunas.salonunasapi.model.AplicaPara
import com.salonunas.salonunasapi.model.TipoServicio
import java.math.BigDecimal
import java.time.LocalDateTime

data class ServicioResponse(
    val id: Long,
    val nombre: String,
    val tipo: TipoServicio,
    val aplicaPara: AplicaPara,
    val descripcion: String?,
    val precio: BigDecimal,
    val activo: Boolean,
    val fechaCreacion: LocalDateTime
)
