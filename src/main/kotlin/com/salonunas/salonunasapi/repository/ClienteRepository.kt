package com.salonunas.salonunasapi.repository

import com.salonunas.salonunasapi.model.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClienteRepository : JpaRepository<Cliente, Long> {
    fun findByTelefono(telefono: String): Optional<Cliente>
    fun existsByTelefono(telefono: String): Boolean
}