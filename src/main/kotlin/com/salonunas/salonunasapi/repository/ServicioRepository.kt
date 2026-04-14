package com.salonunas.salonunasapi.repository

import com.salonunas.salonunasapi.model.AplicaPara
import com.salonunas.salonunasapi.model.Servicio
import com.salonunas.salonunasapi.model.TipoServicio
import org.springframework.data.jpa.repository.JpaRepository

interface ServicioRepository : JpaRepository<Servicio, Long> {
    fun findByTipo(tipo: TipoServicio): List<Servicio>
    fun findByAplicaPara(aplicaPara: AplicaPara): List<Servicio>
    fun findByTipoAndAplicaPara(tipo: TipoServicio, aplicaPara: AplicaPara): List<Servicio>
    fun findByActivo(activo: Boolean): List<Servicio>
    fun existsByNombreAndTipoAndAplicaPara(nombre: String, tipo: TipoServicio, aplicaPara: AplicaPara): Boolean
}
