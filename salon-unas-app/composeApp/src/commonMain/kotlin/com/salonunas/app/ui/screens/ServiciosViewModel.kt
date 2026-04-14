package com.salonunas.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salonunas.app.data.model.Servicio
import com.salonunas.app.data.model.ServicioRequest
import com.salonunas.app.data.repository.SalonRepository
import com.salonunas.app.ui.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServiciosViewModel(
    private val repository: SalonRepository = SalonRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Servicio>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Servicio>>> = _state.asStateFlow()

    fun load() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            runCatching { repository.getServicios() }
                .onSuccess { _state.value = UiState.Success(it) }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo cargar servicios: ${it.message ?: "error desconocido"}"
                    )
                }
        }
    }

    fun create(request: ServicioRequest) {
        viewModelScope.launch {
            runCatching { repository.createServicio(request) }
                .onSuccess { load() }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo crear el servicio: ${it.message ?: "error"}"
                    )
                }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            runCatching { repository.deleteServicio(id) }
                .onSuccess { load() }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo eliminar: ${it.message ?: "error"}"
                    )
                }
        }
    }
}
