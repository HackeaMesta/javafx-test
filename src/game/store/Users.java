package game.store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Label;

/**
 *
 * @author @HackeaMesta
 */
public class Users {

    private String user;
    private String email;
    private String password;
    private String password2;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private String foto;

    private Label notification;
    private Connector conn;

    public Users(String user, String email, String password, String password2, String nombre, String apellido_p, String apellido_m, String foto, Label notification) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.nombre = nombre;
        this.apellido_p = apellido_p;
        this.apellido_m = apellido_m;
        this.foto = foto;

        this.notification = notification;
        this.conn = new Connector();
    }

    public boolean checkEmail() throws ClassNotFoundException, SQLException {
        boolean valido = false;
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            int num_users = 0;
            conn.open();
            //Valida que no exista el usuario previamente
            String user_query = "SELECT * FROM USUARIOS WHERE CORREO = '" + email + "';";
            ResultSet userName = conn.getData(user_query);

            while (userName.next()) {
                num_users++;
            }

            conn.close();

            if (num_users > 0) {
                valido = false;
            } else {
                valido = true;
            }
        }
        return valido;
    }

    public boolean notExistUser() throws ClassNotFoundException, SQLException {
        boolean status = false;
        int num_users = 0;
        conn.open();
        //Valida que no exista el usuario previamente
        String user_query = "SELECT * FROM USUARIOS WHERE NICKNAME = '" + user + "';";
        ResultSet userName = conn.getData(user_query);

        while (userName.next()) {
            num_users++;
        }

        conn.close();

        if (num_users > 0) {
            status = false;
        } else {
            status = true;
        }

        return status;
    }

    public boolean checkPassword() {
        boolean status = false;
        int nums = 0, chars = 0;
        if (password.length() >= 6) {
            if (password == null ? password2 == null : password.equals(password2)) {
                for (int x = 0; x < password.length(); x++) {
                    if ((password.charAt(x) == '0') || (password.charAt(x) == '1')
                            || (password.charAt(x) == '2') || (password.charAt(x) == '3')
                            || (password.charAt(x) == '4') || (password.charAt(x) == '5')
                            || (password.charAt(x) == '6') || (password.charAt(x) == '7')
                            || (password.charAt(x) == '8') || (password.charAt(x) == '9')) {
                        nums++;
                    }
                }
                if (nums > 0) {
                    for (int x = 0; x < password.length(); x++) {
                        if ((password.charAt(x) == '*') || (password.charAt(x) == '/')
                                || (password.charAt(x) == '?') || (password.charAt(x) == '!')
                                || (password.charAt(x) == '.') || (password.charAt(x) == ',')
                                || (password.charAt(x) == '&') || (password.charAt(x) == '$')) {
                            chars++;
                        }
                    }
                    if (chars > 0) {
                        status = true;
                    } else {
                        notification.setText("La contraseña debe incluir un caracter especial: * / ? ! . , & $");
                    }
                } else {
                    notification.setText("La contraseña debe incluir un numero.");
                }
            } else {
                notification.setText("Las contraseñas no coinciden.");
                status = false;
            }
        } else {
            notification.setText("La contraseña debe ser mayor a 5 caracteres.");
            status = false;
        }
        return status;
    }

    public boolean validateUser() throws ClassNotFoundException, SQLException {
        boolean status = false;
        //Valida que el usuario no este vacio
        if (user != "") {
            //Valida que las contrasenias sean iguales y no esten vacias
            if (checkPassword()) {
                //valida el email
                if (checkEmail()) {
                    //Valida si ya existe el usuario
                    if (notExistUser()) {
                        status = true;
                    } else {
                        notification.setText("El usuario ya esta registrado.");
                        status = false;
                    }
                } else {
                    notification.setText("El email no es valido o ya esta registrado.");
                    status = false;
                }
            }
        } else {
            notification.setText("Ingresa un usuario.");
            status = false;
        }
        return status;
    }

    public boolean createUser() throws ClassNotFoundException, SQLException {
        conn.open();

        String query = "INSERT INTO USUARIOS (NICKNAME, CORREO, CONTRASENA, NOMBRE, A_PARTERNO, A_MATERNO, FOTO, TIPO_USER) "
                + "VALUES ('" + user + "', '" + email + "', '" + password + "', '" + nombre + "', '" + apellido_p + "', '" + apellido_m + "', '" + foto + "', 2);";

        if (conn.execute(query)) {
            conn.close();
            return true;
        } else {
            return false;
        }
    }

    public boolean updateUser() throws ClassNotFoundException, SQLException {
        boolean status = false;
        conn.open();

        if (checkPassword()) {
            String query = "UPDATE USUARIOS " + "SET CORREO = '" + email
                    + "', CONTRASENA = '" + password + "', NOMBRE = '" + nombre
                    + "', A_PARTERNO = '" + apellido_p + "', A_MATERNO = '" + apellido_m
                    + "', FOTO = '" + foto + "' WHERE NICKNAME = '" + user + "';";
            if (conn.execute(query)) {
                conn.close();
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    }
    
    public boolean deleteUser() throws ClassNotFoundException, SQLException {
        boolean status = false;
        conn.open();

        if (user != "") {
            if (conn.execute("DELETE FROM USUARIOS WHERE NICKNAME = " + user + ";")) {
                conn.close();
                status = true;
            } else {
                status = false;
            }
        } else {
            notification.setText("Ingresa un usuario");
        }
        return status;
    }
}
