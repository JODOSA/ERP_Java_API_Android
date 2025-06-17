package com.empresa.erp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

import static com.empresa.erp.utils.AlertaUtils.agregarIcono;

public class ConfirmationDialog {

        public static boolean mostrar(){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de borrado");
            alert.setHeaderText("¿Está seguro/a de que desea borrar este registro?");
            alert.setContentText("Esta operación será irreversible");

            agregarIcono(alert);

            ButtonType si = new ButtonType("Si");
            ButtonType no = new ButtonType("No");

            alert.getButtonTypes().setAll(si, no);

            Button btnSi = (Button) alert.getDialogPane().lookupButton(si);
            btnSi.setDefaultButton(false);

            Button btnNo = (Button) alert.getDialogPane().lookupButton(no);
            btnNo.setDefaultButton(true);

            Optional<ButtonType> resultado = alert.showAndWait();
            return resultado.isPresent() && resultado.get() == si;
        }
}

