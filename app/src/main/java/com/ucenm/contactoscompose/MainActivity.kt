package com.ucenm.contactoscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucenm.contactoscompose.ui.theme.ContactosComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactosComposeTheme {
                ContactosScreen()
            }
        }
    }
}

@Composable
fun ContactosScreen() {
    val nombre = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val nombreError = remember { mutableStateOf(false) }
    val correoError = remember { mutableStateOf(false) }
    val contactos: SnapshotStateList<Contacto> = remember { mutableStateListOf() }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Nuevo contacto", style = MaterialTheme.typography.headlineMedium)
            Text("Completa los datos para guardar un contacto.", style = MaterialTheme.typography.bodyMedium)

            OutlinedTextField(
                value = nombre.value,
                onValueChange = {
                    nombre.value = it
                    if (nombreError.value && ValidadorJava.validarNombre(it)) nombreError.value = false
                },
                label = { Text("Nombre") },
                isError = nombreError.value,
                supportingText = if (nombreError.value) {
                    { Text("El nombre es obligatorio.") }
                } else null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = correo.value,
                onValueChange = {
                    correo.value = it
                    if (correoError.value && ValidadorJava.validarCorreo(it)) correoError.value = false
                },
                label = { Text("Correo electrónico") },
                isError = correoError.value,
                supportingText = if (correoError.value) {
                    { Text("Escribe un correo válido, por ejemplo nombre@dominio.com.") }
                } else null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = telefono.value,
                onValueChange = { telefono.value = it },
                label = { Text("Teléfono (opcional)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val nombreValido = ValidadorJava.validarNombre(nombre.value)
                    val correoValido = ValidadorJava.validarCorreo(correo.value)
                    nombreError.value = !nombreValido
                    correoError.value = !correoValido
                    if (nombreValido && correoValido) {
                        contactos.add(
                            Contacto(
                                nombre.value.trim(),
                                correo.value.trim(),
                                telefono.value.trim().ifBlank { null }
                            )
                        )
                        nombre.value = ""
                        correo.value = ""
                        telefono.value = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar contacto")
            }

            if (contactos.isNotEmpty()) {
                Text("Contactos guardados", style = MaterialTheme.typography.titleLarge)
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(contactos) { contacto ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(contacto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(contacto.correo)
                                Text(contacto.telefono ?: "Sin teléfono")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactosScreenPreview() {
    ContactosComposeTheme { ContactosScreen() }
}
