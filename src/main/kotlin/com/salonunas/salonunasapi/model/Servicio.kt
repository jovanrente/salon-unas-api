package com.salonunas.salonunasapi.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

enum class TipoServicio {
    PERMANENTE,
    SEMI_PERMANENTE,
    ARREGLO_UNAS
}

enum class AplicaPara {
    MANOS,
    PIES,
    MANOS_Y_PIES
}

@Entity
@Table(name = "servicios")
data class Servicio(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var nombre: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var tipo: TipoServicio,

    @Enumerated(EnumType.STRING)
    @Column(name = "aplica_para", nullable = false)
    var aplicaPara: AplicaPara,

    @Column
    var descripcion: String? = null,

    @Column(nullable = false, precision = 10, scale = 2)
    var precio: BigDecimal,

    @Column(nullable = false)
    var activo: Boolean = true,

    @Column(name = "fecha_creacion")
    val fechaCreacion: LocalDateTime = LocalDateTime.now()
)
