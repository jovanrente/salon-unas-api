package com.salonunas.salonunasapi.controller

import com.salonunas.salonunasapi.dto.ManicuristaRequest
import com.salonunas.salonunasapi.dto.ManicuristaResponse
import com.salonunas.salonunasapi.service.ManicuristaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/manicuristas")
class ManicuristaController(private val manicuristaService: ManicuristaService) {

    @GetMapping
    fun obtenerTodos(): ResponseEntity<List<ManicuristaResponse>> {
        return ResponseEntity.ok(manicuristaService.obtenerTodos())
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<ManicuristaResponse> {
        val manicurista = manicuristaService.obtenerPorId(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(manicurista)
    }

    @PostMapping
    fun crear(@RequestBody request: ManicuristaRequest): ResponseEntity<Any> {
        return try {
            val nueva = manicuristaService.crear(request)
            ResponseEntity.status(HttpStatus.CREATED).body(nueva)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody request: ManicuristaRequest): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(manicuristaService.actualizar(id, request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        manicuristaService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}
