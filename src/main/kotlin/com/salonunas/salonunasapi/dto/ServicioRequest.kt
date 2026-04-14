package com.salonunas.salonunasapi.dto

import com.salonunas.salonunasapi.model.AplicaPara
import com.salonunas.salonunasapi.model.TipoServicio
import java.math.BigDecimal

data class ServicioRequest(
    val nombre: String,
    val tipo: TipoServicio,
    val aplicaPara: AplicaPara,
    val descripcion: String? = null,
    val precio: BigDecimal,
    val activo: Boolean = true
)
