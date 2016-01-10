package game.store;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author @HackeaMesta
 */

public class session {
    public static String tipo_usuario;
    public static String nickname;
    public static String perfil;

    private Connector conn;

    public session() {
        /*
        * 0 para Administrador
        * 1 para Tester
        * 2 para Usuario Estandar
        */
        this.tipo_usuario = "2";
        this.nickname = null;
        this.perfil = null;
        this.conn = new Connector();
    }  
    
    public boolean isAdmin() {
        if (this.tipo_usuario == "0") {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isTester() {
        if (this.tipo_usuario == "1") {
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String nickname, String password) throws ClassNotFoundException, SQLException {
        boolean status = false;
        if (nickname != "" && password != "") {
            int num_users = 0;
            conn.open();;
            ResultSet user = conn.getData("SELECT * FROM USUARIOS WHERE NICKNAME = '" + nickname + "' AND CONTRASENA = '" + password + "';");

            while (user.next()) {
                this.tipo_usuario = user.getString(8);
                this.nickname = user.getString(1);
                num_users++;
            }

            if (num_users == 1) {
                status = true;
            } else {
                status = false;
            }

            conn.close();
        }
        return status;
    }
}
