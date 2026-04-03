package com.salonunas.salonunasapi.service

import com.salonunas.salonunasapi.dto.ManicuristaRequest
import com.salonunas.salonunasapi.dto.ManicuristaResponse
import com.salonunas.salonunasapi.model.Manicurista
import com.salonunas.salonunasapi.repository.ManicuristaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ManicuristaService(private val manicuristaRepository: ManicuristaRepository) {

    fun obtenerTodos(): List<ManicuristaResponse> {
        return manicuristaRepository.findAll().map { it.toResponse() }
    }

    fun obtenerPorId(id: Long): ManicuristaResponse? {
        return manicuristaRepository.findById(id).orElse(null)?.toResponse()
    }

    fun crear(request: ManicuristaRequest): ManicuristaResponse {
        if (manicuristaRepository.existsByTelefono(request.telefono)) {
            throw IllegalArgumentException("Ya existe una manicurista con el teléfono ${request.telefono}")
        }

        val manicurista = Manicurista(
            nombre = request.nombre,
            telefono = request.telefono,
            especialidad = request.especialidad
        )
        return manicuristaRepository.save(manicurista).toResponse()
    }

    fun actualizar(id: Long, request: ManicuristaRequest): ManicuristaResponse {
        val manicurista = manicuristaRepository.findById(id).orElseThrow {
            IllegalArgumentException("Manicurista con id $id no encontrada")
        }

        if (manicurista.telefono != request.telefono && manicuristaRepository.existsByTelefono(request.telefono)) {
            throw IllegalArgumentException("Ya existe una manicurista con el teléfono ${request.telefono}")
        }

        manicurista.nombre = request.nombre
        manicurista.telefono = request.telefono
        manicurista.especialidad = request.especialidad

        return manicuristaRepository.save(manicurista).toResponse()
    }

    fun eliminar(id: Long) {
        manicuristaRepository.deleteById(id)
    }

    private fun Manicurista.toResponse() = ManicuristaResponse(
        id = this.id!!,
        nombre = this.nombre,
        telefono = this.telefono,
        especialidad = this.especialidad,
        fechaRegistro = this.fechaRegistro
    )
}
