package com.ucenm.contactoscompose

/** Representa un contacto capturado en el formulario. */
data class Contacto(
    val nombre: String,
    val correo: String,
    val telefono: String?
)
