package com.salonunas.salonunasapi.service

import com.salonunas.salonunasapi.dto.ServicioRequest
import com.salonunas.salonunasapi.dto.ServicioResponse
import com.salonunas.salonunasapi.model.AplicaPara
import com.salonunas.salonunasapi.model.Servicio
import com.salonunas.salonunasapi.model.TipoServicio
import com.salonunas.salonunasapi.repository.ServicioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ServicioService(private val servicioRepository: ServicioRepository) {

    fun obtenerTodos(): List<ServicioResponse> {
        return servicioRepository.findAll().map { it.toResponse() }
    }

    fun obtenerActivos(): List<ServicioResponse> {
        return servicioRepository.findByActivo(true).map { it.toResponse() }
    }

    fun obtenerPorId(id: Long): ServicioResponse? {
        return servicioRepository.findById(id).orElse(null)?.toResponse()
    }

    fun obtenerPorTipo(tipo: TipoServicio): List<ServicioResponse> {
        return servicioRepository.findByTipo(tipo).map { it.toResponse() }
    }

    fun obtenerPorAplicaPara(aplicaPara: AplicaPara): List<ServicioResponse> {
        return servicioRepository.findByAplicaPara(aplicaPara).map { it.toResponse() }
    }

    fun obtenerPorTipoYAplicaPara(tipo: TipoServicio, aplicaPara: AplicaPara): List<ServicioResponse> {
        return servicioRepository.findByTipoAndAplicaPara(tipo, aplicaPara).map { it.toResponse() }
    }

    fun crear(request: ServicioRequest): ServicioResponse {
        if (servicioRepository.existsByNombreAndTipoAndAplicaPara(request.nombre, request.tipo, request.aplicaPara)) {
            throw IllegalArgumentException("Ya existe un servicio '${request.nombre}' de tipo ${request.tipo} para ${request.aplicaPara}")
        }

        val servicio = Servicio(
            nombre = request.nombre,
            tipo = request.tipo,
            aplicaPara = request.aplicaPara,
            descripcion = request.descripcion,
            precio = request.precio,
            activo = request.activo
        )
        return servicioRepository.save(servicio).toResponse()
    }

    fun actualizar(id: Long, request: ServicioRequest): ServicioResponse {
        val servicio = servicioRepository.findById(id).orElseThrow {
            IllegalArgumentException("Servicio con id $id no encontrado")
        }

        val nombreCambia = servicio.nombre != request.nombre
                || servicio.tipo != request.tipo
                || servicio.aplicaPara != request.aplicaPara
        if (nombreCambia && servicioRepository.existsByNombreAndTipoAndAplicaPara(request.nombre, request.tipo, request.aplicaPara)) {
            throw IllegalArgumentException("Ya existe un servicio '${request.nombre}' de tipo ${request.tipo} para ${request.aplicaPara}")
        }

        servicio.nombre = request.nombre
        servicio.tipo = request.tipo
        servicio.aplicaPara = request.aplicaPara
        servicio.descripcion = request.descripcion
        servicio.precio = request.precio
        servicio.activo = request.activo

        return servicioRepository.save(servicio).toResponse()
    }

    fun eliminar(id: Long) {
        servicioRepository.deleteById(id)
    }

    private fun Servicio.toResponse() = ServicioResponse(
        id = this.id!!,
        nombre = this.nombre,
        tipo = this.tipo,
        aplicaPara = this.aplicaPara,
        descripcion = this.descripcion,
        precio = this.precio,
        activo = this.activo,
        fechaCreacion = this.fechaCreacion
    )
}
