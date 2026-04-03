package com.salonunas.salonunasapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "manicuristas")
data class Manicurista(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var nombre: String,

    @Column(nullable = false, unique = true)
    var telefono: String,

    @Column(nullable = false)
    var especialidad: String,

    @Column(name = "fecha_registro")
    val fechaRegistro: LocalDateTime = LocalDateTime.now()
)
