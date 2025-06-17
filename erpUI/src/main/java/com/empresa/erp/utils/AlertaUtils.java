package com.empresa.erp.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertaUtils {

    public static void mostrar(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // Añadir icono al Alert
        agregarIcono(alerta);

        alerta.showAndWait();
    }

    // Método reutilizable para añadir icono
    public static void agregarIcono(Alert alerta) {
        try {
            Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
            var iconStream = AlertaUtils.class.getResourceAsStream("/images/corporate_logo_white.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            } else {
                System.err.println("No se encontró el icono de la empresa para el Alert.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
