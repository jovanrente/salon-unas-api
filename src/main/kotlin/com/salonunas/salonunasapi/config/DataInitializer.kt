package com.salonunas.salonunasapi.config

import com.salonunas.salonunasapi.model.AplicaPara
import com.salonunas.salonunasapi.model.Servicio
import com.salonunas.salonunasapi.model.TipoServicio
import com.salonunas.salonunasapi.repository.ServicioRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataInitializer(private val servicioRepository: ServicioRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (servicioRepository.count() == 0L) {
            val servicios = listOf(
                // --- PERMANENTE ---
                Servicio(
                    nombre = "Esmaltado Permanente Manos",
                    tipo = TipoServicio.PERMANENTE,
                    aplicaPara = AplicaPara.MANOS,
                    descripcion = "Esmaltado permanente para manos con gel de larga duración",
                    precio = BigDecimal("250.00")
                ),
                Servicio(
                    nombre = "Esmaltado Permanente Pies",
                    tipo = TipoServicio.PERMANENTE,
                    aplicaPara = AplicaPara.PIES,
                    descripcion = "Esmaltado permanente para pies con gel de larga duración",
                    precio = BigDecimal("220.00")
                ),
                Servicio(
                    nombre = "Esmaltado Permanente Manos y Pies",
                    tipo = TipoServicio.PERMANENTE,
                    aplicaPara = AplicaPara.MANOS_Y_PIES,
                    descripcion = "Esmaltado permanente completo para manos y pies",
                    precio = BigDecimal("420.00")
                ),
                // --- SEMI-PERMANENTE ---
                Servicio(
                    nombre = "Esmaltado Semi-Permanente Manos",
                    tipo = TipoServicio.SEMI_PERMANENTE,
                    aplicaPara = AplicaPara.MANOS,
                    descripcion = "Esmaltado semi-permanente para manos, duración de 2 a 3 semanas",
                    precio = BigDecimal("180.00")
                ),
                Servicio(
                    nombre = "Esmaltado Semi-Permanente Pies",
                    tipo = TipoServicio.SEMI_PERMANENTE,
                    aplicaPara = AplicaPara.PIES,
                    descripcion = "Esmaltado semi-permanente para pies, duración de 2 a 3 semanas",
                    precio = BigDecimal("160.00")
                ),
                Servicio(
                    nombre = "Esmaltado Semi-Permanente Manos y Pies",
                    tipo = TipoServicio.SEMI_PERMANENTE,
                    aplicaPara = AplicaPara.MANOS_Y_PIES,
                    descripcion = "Esmaltado semi-permanente completo para manos y pies",
                    precio = BigDecimal("320.00")
                ),
                // --- ARREGLO DE UÑAS ---
                Servicio(
                    nombre = "Arreglo de Uñas Dañadas Manos",
                    tipo = TipoServicio.ARREGLO_UNAS,
                    aplicaPara = AplicaPara.MANOS,
                    descripcion = "Reparación de uñas rotas o dañadas en manos",
                    precio = BigDecimal("80.00")
                ),
                Servicio(
                    nombre = "Arreglo de Uñas Dañadas Pies",
                    tipo = TipoServicio.ARREGLO_UNAS,
                    aplicaPara = AplicaPara.PIES,
                    descripcion = "Reparación de uñas rotas o dañadas en pies",
                    precio = BigDecimal("70.00")
                ),
                Servicio(
                    nombre = "Arreglo de Uñas Dañadas Manos y Pies",
                    tipo = TipoServicio.ARREGLO_UNAS,
                    aplicaPara = AplicaPara.MANOS_Y_PIES,
                    descripcion = "Reparación de uñas rotas o dañadas en manos y pies",
                    precio = BigDecimal("140.00")
                )
            )
            servicioRepository.saveAll(servicios)
            println("✅ Datos de prueba insertados: ${servicios.size} servicios")
        } else {
            println("ℹ️  Tabla servicios ya contiene datos, no se insertan duplicados")
        }
    }
}
