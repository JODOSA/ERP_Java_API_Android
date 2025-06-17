package com.empresa.erp;

import com.empresa.erp.views.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView(stage);
        Scene loginScene = loginView.getScene();

        stage.setScene(loginScene);
        stage.setTitle("Sistema ERP - Login");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}