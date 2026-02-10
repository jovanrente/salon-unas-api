package com.salonunas.salonunasapi.controller

import com.salonunas.salonunasapi.dto.ClienteRequest
import com.salonunas.salonunasapi.dto.ClienteResponse
import com.salonunas.salonunasapi.service.ClienteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clientes")
class ClienteController(private val clienteService: ClienteService) {

    @GetMapping
    fun obtenerTodos(): ResponseEntity<List<ClienteResponse>> {
        return ResponseEntity.ok(clienteService.obtenerTodos())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<ClienteResponse> {
        val cliente = clienteService.obtenerPorId(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(cliente)
    }

    @PostMapping
    fun crear(@RequestBody request: ClienteRequest): ResponseEntity<Any> {
        return try {
            val nuevoCliente = clienteService.crear(request)
            ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        clienteService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}
