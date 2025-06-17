package com.empresa.erp.views;

import com.empresa.erp.controllers.LoginController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginView {
    private final Stage stage;
    private final LoginController loginController;

    public LoginView(Stage stage){
        this.stage = stage;
        this.loginController = new LoginController(stage);

        // Logo en la esquina de la ventana
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white.png"))));
    }

    public Scene getScene(){

        // Fondo con imagen
        ImageView fondo = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/fondoLogin02.png"))));
        fondo.setFitWidth(600);
        fondo.setFitHeight(400);
        fondo.setPreserveRatio(false);

        // Crear componentes
        Label lblUser = new Label("Usuario:");
        TextField txtUser = new TextField();
        Label lblPass =new Label("Contraseña:");
        PasswordField txtPass = new PasswordField();
        Button btnLogin = new Button("Ingresar");
        Label lblError = new Label();

        // Estilo campos y etiquetas
        lblUser.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        lblPass.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        txtUser.setPromptText("Introduce tu usuario");
        txtPass.setPromptText("Introduce tu contraseña");

        // Botón con estilo moderno
        btnLogin.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-background-radius: 10;
            -fx-padding: 8 20;
        """);
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle("""
            -fx-background-color: #66a7bb;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-background-radius: 10;
            -fx-padding: 8 20;
        """));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle("""
            -fx-background-color: #66a7bb;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-background-radius: 10;
            -fx-padding: 8 20;
        """));

        // Caja blanca semitransparente con borde redondeado
        VBox loginBox = new VBox(10, lblUser, txtUser, lblPass, txtPass, btnLogin, lblError);
        loginBox.setAlignment(Pos.CENTER_LEFT);
        loginBox.setPadding(new Insets(20));
        loginBox.setMaxWidth(200);
        loginBox.setMaxHeight(150);
        loginBox.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.85);
            -fx-background-radius: 15;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 4);
        """);

        // Contenedor general
        StackPane rootPane = new StackPane(fondo, loginBox);
        StackPane.setAlignment(loginBox, Pos.CENTER_LEFT);
        StackPane.setMargin(loginBox, new Insets(0, 0, 0, 50));

        // Acción del botón de login
        btnLogin.setOnAction(event -> {
            String username = txtUser.getText();
            String password = txtPass.getText();

            if(loginController.autenticarUsuario(username, password)){
                lblError.setText("Bienvenido, " + username);
                lblError.setStyle("-fx-text-fill: green;");
                // Aquí se abrirá la siguiente escena del menú principal
                Platform.runLater(() -> {
                    loginController.abrirMenuPrincipal();
                    stage.close();
                });
            }else{
                lblError.setText("Usuario o contraseña incorrectos.");
                lblError.setStyle("-fx-text-fill: red;");
            }
        });
        return new Scene(rootPane, 600, 400);
    }
}
