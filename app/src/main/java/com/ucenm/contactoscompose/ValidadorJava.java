package com.ucenm.contactoscompose;

import java.util.regex.Pattern;

/** Validaciones sencillas reutilizables desde Kotlin. */
public final class ValidadorJava {
    private static final Pattern FORMATO_CORREO =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private ValidadorJava() {
        // No se instancia: sus métodos son estáticos.
    }

    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public static boolean validarCorreo(String correo) {
        return correo != null && FORMATO_CORREO.matcher(correo.trim()).matches();
    }
}
