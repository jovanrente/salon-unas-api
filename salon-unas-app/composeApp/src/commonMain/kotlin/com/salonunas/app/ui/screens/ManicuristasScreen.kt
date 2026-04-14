package com.salonunas.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salonunas.app.data.model.ManicuristaRequest
import com.salonunas.app.ui.components.EmptyView
import com.salonunas.app.ui.components.ErrorView
import com.salonunas.app.ui.components.LoadingView
import com.salonunas.app.ui.components.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManicuristasScreen(
    viewModel: ManicuristasViewModel = remember { ManicuristasViewModel() }
) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) { viewModel.load() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Manicuristas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar manicurista")
            }
        }
    ) { padding ->
        when (val current = state) {
            is UiState.Loading -> LoadingView(Modifier.padding(padding))
            is UiState.Error -> ErrorView(
                current.message,
                onRetry = { scope.launch { viewModel.load() } },
                modifier = Modifier.padding(padding)
            )
            is UiState.Success -> {
                if (current.data.isEmpty()) {
                    EmptyView("No hay manicuristas aún.", Modifier.padding(padding))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(current.data, key = { it.id }) { m ->
                            Card(Modifier.fillMaxWidth()) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(m.nombre, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        "Especialidad: ${m.especialidad}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        m.telefono,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    IconButton(
                                        onClick = { scope.launch { viewModel.delete(m.id) } },
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        NuevaManicuristaDialog(
            onDismiss = { showDialog = false },
            onConfirm = { nombre, telefono, especialidad ->
                scope.launch {
                    viewModel.create(ManicuristaRequest(nombre, telefono, especialidad))
                    showDialog = false
                }
            }
        )
    }
}

@Composable
private fun NuevaManicuristaDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva manicurista") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(telefono, { telefono = it }, label = { Text("Teléfono") })
                OutlinedTextField(
                    especialidad,
                    { especialidad = it },
                    label = { Text("Especialidad") }
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = nombre.isNotBlank() && telefono.isNotBlank() && especialidad.isNotBlank(),
                onClick = {
                    onConfirm(nombre.trim(), telefono.trim(), especialidad.trim())
                }
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
