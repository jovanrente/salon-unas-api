package com.salonunas.app.data.remote

import com.salonunas.app.data.model.Cliente
import com.salonunas.app.data.model.ClienteRequest
import com.salonunas.app.data.model.Manicurista
import com.salonunas.app.data.model.ManicuristaRequest
import com.salonunas.app.data.model.Servicio
import com.salonunas.app.data.model.ServicioRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class SalonApi(
    private val client: HttpClient = HttpClientFactory.create(),
    private val baseUrl: String = ApiConfig.baseUrl
) {

    // --- Clientes ---
    suspend fun listClientes(): List<Cliente> =
        client.get("$baseUrl/api/clientes").body()

    suspend fun createCliente(request: ClienteRequest): Cliente =
        client.post("$baseUrl/api/clientes") { setBody(request) }.body()

    suspend fun deleteCliente(id: Long) {
        client.delete("$baseUrl/api/clientes/$id")
    }

    // --- Manicuristas ---
    suspend fun listManicuristas(): List<Manicurista> =
        client.get("$baseUrl/api/manicuristas").body()

    suspend fun createManicurista(request: ManicuristaRequest): Manicurista =
        client.post("$baseUrl/api/manicuristas") { setBody(request) }.body()

    suspend fun deleteManicurista(id: Long) {
        client.delete("$baseUrl/api/manicuristas/$id")
    }

    // --- Servicios ---
    suspend fun listServicios(): List<Servicio> =
        client.get("$baseUrl/api/servicios").body()

    suspend fun createServicio(request: ServicioRequest): Servicio =
        client.post("$baseUrl/api/servicios") { setBody(request) }.body()

    suspend fun deleteServicio(id: Long) {
        client.delete("$baseUrl/api/servicios/$id")
    }
}
