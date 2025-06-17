package com.empresa.erp.controllers;

import empresa.utils.UserSession;
import com.empresa.erp.views.MainMenuView;
import empresa.dao.UsuariosDAO;
import empresa.models.Usuarios;
import empresa.utils.PasswordHelper;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {

    private final UsuariosDAO usuariosDAO;
    private final Stage stage;

    public LoginController(Stage stage){
        this.usuariosDAO = new UsuariosDAO();
        this.stage = stage;

    }

    public boolean autenticarUsuario(String username, String password){
        // Obtenemos la lista de usuarios desde la BD
        List<Usuarios> listaUsuarios = usuariosDAO.readAll();

        // Busca si existe el usuario que se ha introducido
        for(Usuarios usuario : listaUsuarios){
            if(usuario.getNombreUs().equals(username)){
                // Ahora comprueba la contraseña
                String hashedInputPass = PasswordHelper.hashPassword(password);

                if(usuario.getPassword().equals(hashedInputPass)){
                    UserSession.setUsuario(usuario); // Guardamos el usuario de la sesión
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public void abrirMenuPrincipal(){
        Platform.runLater(() -> {
            MainMenuView mainMenuView = new MainMenuView(new Stage());
            mainMenuView.show();
            stage.close();
        });
    }
}
