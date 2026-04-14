package com.salonunas.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import com.salonunas.app.data.model.AplicaPara
import com.salonunas.app.data.model.ServicioRequest
import com.salonunas.app.data.model.TipoServicio
import com.salonunas.app.ui.components.EmptyView
import com.salonunas.app.ui.components.ErrorView
import com.salonunas.app.ui.components.LoadingView
import com.salonunas.app.ui.components.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosScreen(
    viewModel: ServiciosViewModel = remember { ServiciosViewModel() }
) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) { viewModel.load() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Servicios") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar servicio")
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
                    EmptyView("No hay servicios aún.", Modifier.padding(padding))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(current.data, key = { it.id }) { s ->
                            Card(Modifier.fillMaxWidth()) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(
                                        s.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        "$${s.precio}",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    if (!s.descripcion.isNullOrBlank()) {
                                        Text(
                                            s.descripcion,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Box(
                                        Modifier.fillMaxWidth().padding(top = 8.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            AssistChip(
                                                onClick = {},
                                                label = { Text(s.tipo.etiqueta()) }
                                            )
                                            AssistChip(
                                                onClick = {},
                                                label = { Text(s.aplicaPara.etiqueta()) }
                                            )
                                        }
                                    }
                                    IconButton(
                                        onClick = { scope.launch { viewModel.delete(s.id) } },
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
        NuevoServicioDialog(
            onDismiss = { showDialog = false },
            onConfirm = { request ->
                scope.launch {
                    viewModel.create(request)
                    showDialog = false
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NuevoServicioDialog(
    onDismiss: () -> Unit,
    onConfirm: (ServicioRequest) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf(TipoServicio.PERMANENTE) }
    var aplicaPara by remember { mutableStateOf(AplicaPara.MANOS) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo servicio") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(
                    precio,
                    { precio = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    descripcion,
                    { descripcion = it },
                    label = { Text("Descripción (opcional)") }
                )
                EnumDropdown(
                    label = "Tipo",
                    selected = tipo,
                    options = TipoServicio.entries,
                    optionLabel = { it.etiqueta() },
                    onSelected = { tipo = it }
                )
                EnumDropdown(
                    label = "Aplica para",
                    selected = aplicaPara,
                    options = AplicaPara.entries,
                    optionLabel = { it.etiqueta() },
                    onSelected = { aplicaPara = it }
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = nombre.isNotBlank() && precio.toDoubleOrNull() != null,
                onClick = {
                    onConfirm(
                        ServicioRequest(
                            nombre = nombre.trim(),
                            tipo = tipo,
                            aplicaPara = aplicaPara,
                            descripcion = descripcion.trim().ifBlank { null },
                            precio = precio.toDouble(),
                            activo = true
                        )
                    )
                }
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T : Enum<T>> EnumDropdown(
    label: String,
    selected: T,
    options: List<T>,
    optionLabel: (T) -> String,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = optionLabel(selected),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(optionLabel(option)) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
