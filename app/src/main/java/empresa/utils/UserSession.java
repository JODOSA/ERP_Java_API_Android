package empresa.utils;

import empresa.models.Usuarios;

public class UserSession {

    private static Usuarios currentUser;

    public static void setUsuario(Usuarios usuario) {
        currentUser = usuario;
    }

    public static Usuarios getUsuario() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
