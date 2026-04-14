package com.salonunas.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salonunas.app.data.model.Manicurista
import com.salonunas.app.data.model.ManicuristaRequest
import com.salonunas.app.data.repository.SalonRepository
import com.salonunas.app.ui.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManicuristasViewModel(
    private val repository: SalonRepository = SalonRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Manicurista>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Manicurista>>> = _state.asStateFlow()

    fun load() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            runCatching { repository.getManicuristas() }
                .onSuccess { _state.value = UiState.Success(it) }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo cargar manicuristas: ${it.message ?: "error desconocido"}"
                    )
                }
        }
    }

    fun create(request: ManicuristaRequest) {
        viewModelScope.launch {
            runCatching { repository.createManicurista(request) }
                .onSuccess { load() }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo crear la manicurista: ${it.message ?: "error"}"
                    )
                }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            runCatching { repository.deleteManicurista(id) }
                .onSuccess { load() }
                .onFailure {
                    _state.value = UiState.Error(
                        "No se pudo eliminar: ${it.message ?: "error"}"
                    )
                }
        }
    }
}
