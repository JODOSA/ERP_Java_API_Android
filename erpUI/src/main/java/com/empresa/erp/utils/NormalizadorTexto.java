package com.empresa.erp.utils;

import java.text.Normalizer;

public final class NormalizadorTexto {
    private NormalizadorTexto() {
        // Con esto evitamos la instanciación
    }

    public static String limpiarTexto(String texto) {
        if (texto == null) return "";
        String limpio = Normalizer.normalize(texto, Normalizer.Form.NFD);
        limpio = limpio.replaceAll("[\\p{InCombiningDiacriticalMarks}]", ""); // Quita las tildes
        limpio = limpio.replaceAll("[^\\p{L}\\p{Nd}\\s]", ""); // Quita los signos de puntuación
        return limpio.toLowerCase().trim(); // Convierte en minúsculas y quita los espacios sobrantes
    }
}
