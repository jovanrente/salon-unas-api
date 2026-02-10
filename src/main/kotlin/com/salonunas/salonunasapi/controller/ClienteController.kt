package com.salonunas.salonunasapi.controller

import com.salonunas.salonunasapi.model.Cliente
import com.salonunas.salonunasapi.service.ClienteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clientes")
class ClienteController(private val clienteService: ClienteService) {

    @GetMapping
    fun obtenerTodos(): ResponseEntity<List<Cliente>> {
        return ResponseEntity.ok(clienteService.obtenerTodos())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<Cliente> {
        val cliente = clienteService.obtenerPorId(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(cliente)
    }

    @PostMapping
    fun crear(@RequestBody cliente: Cliente): ResponseEntity<Any> {
        return try {
            val nuevoCliente = clienteService.crear(cliente)
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
