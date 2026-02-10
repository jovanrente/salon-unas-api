package com.salonunas.salonunasapi.service

import com.salonunas.salonunasapi.dto.ClienteRequest
import com.salonunas.salonunasapi.dto.ClienteResponse
import com.salonunas.salonunasapi.model.Cliente
import com.salonunas.salonunasapi.repository.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ClienteService(private val clienteRepository: ClienteRepository) {

    fun obtenerTodos(): List<ClienteResponse> {
        return clienteRepository.findAll().map { it.toResponse() }
    }

    fun obtenerPorId(id: Long): ClienteResponse? {
        return clienteRepository.findById(id).orElse(null)?.toResponse()
    }

    fun crear(request: ClienteRequest): ClienteResponse {
        if (clienteRepository.existsByTelefono(request.telefono)) {
            throw IllegalArgumentException("Ya existe un cliente con el teléfono ${request.telefono}")
        }

        val cliente = Cliente(
            nombre = request.nombre,
            telefono = request.telefono
        )
        return clienteRepository.save(cliente).toResponse()
    }

    fun eliminar(id: Long) {
        clienteRepository.deleteById(id)
    }

    private fun Cliente.toResponse() = ClienteResponse(
        id = this.id!!,
        nombre = this.nombre,
        telefono = this.telefono,
        fechaRegistro = this.fechaRegistro
    )
}
