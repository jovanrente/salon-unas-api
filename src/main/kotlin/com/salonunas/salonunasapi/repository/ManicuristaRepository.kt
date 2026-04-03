package com.salonunas.salonunasapi.repository

import com.salonunas.salonunasapi.model.Manicurista
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ManicuristaRepository : JpaRepository<Manicurista, Long> {
    fun findByTelefono(telefono: String): Optional<Manicurista>
    fun existsByTelefono(telefono: String): Boolean
}
