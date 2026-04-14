package com.salonunas.app.data.repository

import com.salonunas.app.data.model.Cliente
import com.salonunas.app.data.model.ClienteRequest
import com.salonunas.app.data.model.Manicurista
import com.salonunas.app.data.model.ManicuristaRequest
import com.salonunas.app.data.model.Servicio
import com.salonunas.app.data.model.ServicioRequest
import com.salonunas.app.data.remote.SalonApi

class SalonRepository(private val api: SalonApi = SalonApi()) {

    // Clientes
    suspend fun getClientes(): List<Cliente> = api.listClientes()
    suspend fun createCliente(request: ClienteRequest): Cliente = api.createCliente(request)
    suspend fun deleteCliente(id: Long) = api.deleteCliente(id)

    // Manicuristas
    suspend fun getManicuristas(): List<Manicurista> = api.listManicuristas()
    suspend fun createManicurista(request: ManicuristaRequest): Manicurista =
        api.createManicurista(request)
    suspend fun deleteManicurista(id: Long) = api.deleteManicurista(id)

    // Servicios
    suspend fun getServicios(): List<Servicio> = api.listServicios()
    suspend fun createServicio(request: ServicioRequest): Servicio = api.createServicio(request)
    suspend fun deleteServicio(id: Long) = api.deleteServicio(id)
}
