package com.salonunas.salonunasapi.controller

import com.salonunas.salonunasapi.dto.ServicioRequest
import com.salonunas.salonunasapi.dto.ServicioResponse
import com.salonunas.salonunasapi.model.AplicaPara
import com.salonunas.salonunasapi.model.TipoServicio
import com.salonunas.salonunasapi.service.ServicioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/servicios")
class ServicioController(private val servicioService: ServicioService) {

    @GetMapping
    fun obtenerTodos(
        @RequestParam(required = false) tipo: TipoServicio?,
        @RequestParam(required = false) aplicaPara: AplicaPara?,
        @RequestParam(required = false, defaultValue = "false") soloActivos: Boolean
    ): ResponseEntity<List<ServicioResponse>> {
        val resultado = when {
            tipo != null && aplicaPara != null -> servicioService.obtenerPorTipoYAplicaPara(tipo, aplicaPara)
            tipo != null -> servicioService.obtenerPorTipo(tipo)
            aplicaPara != null -> servicioService.obtenerPorAplicaPara(aplicaPara)
            soloActivos -> servicioService.obtenerActivos()
            else -> servicioService.obtenerTodos()
        }
        return ResponseEntity.ok(resultado)
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<ServicioResponse> {
        val servicio = servicioService.obtenerPorId(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(servicio)
    }

    @PostMapping
    fun crear(@RequestBody request: ServicioRequest): ResponseEntity<Any> {
        return try {
            val nuevo = servicioService.crear(request)
            ResponseEntity.status(HttpStatus.CREATED).body(nuevo)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody request: ServicioRequest): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(servicioService.actualizar(id, request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        servicioService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}
