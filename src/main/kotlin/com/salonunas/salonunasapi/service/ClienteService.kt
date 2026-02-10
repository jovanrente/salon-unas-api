package com.salonunas.salonunasapi.service

import com.salonunas.salonunasapi.model.Cliente
import com.salonunas.salonunasapi.repository.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ClienteService(private val clienteRepository: ClienteRepository) {

    fun obtenerTodos(): List<Cliente>{
        return clienteRepository.findAll()
    }

    fun obtenerPorId(id: Long): Cliente? {
        return clienteRepository.findById(id).orElse(null)
    }

    fun crear(cliente: Cliente): Cliente {
        // Validar que no exista el teléfono
        if (clienteRepository.existsByTelefono(cliente.telefono)) {
            throw IllegalArgumentException("Ya existe un cliente con el teléfono ${cliente.telefono}")
        }

        return clienteRepository.save(cliente)
    }

    fun eliminar(id: Long) {
        clienteRepository.deleteById(id)
    }
}