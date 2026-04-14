package com.salonunas.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salonunas.app.data.model.Cliente
import com.salonunas.app.data.model.ClienteRequest
import com.salonunas.app.data.repository.SalonRepository
import com.salonunas.app.ui.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientesViewModel(
    private val repository: SalonRepository = SalonRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Cliente>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Cliente>>> = _state.asStateFlow()

    fun load() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            runCatching { repository.getClientes() }
                .onSuccess { _state.value = UiState.Success(it) }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo cargar clientes: ${it.message ?: "error desconocido"}"
                    )
                }
        }
    }

    fun create(request: ClienteRequest) {
        viewModelScope.launch {
            runCatching { repository.createCliente(request) }
                .onSuccess { load() }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo crear el cliente: ${it.message ?: "error"}"
                    )
                }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            runCatching { repository.deleteCliente(id) }
                .onSuccess { load() }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo eliminar: ${it.message ?: "error"}"
                    )
                }
        }
    }
}
