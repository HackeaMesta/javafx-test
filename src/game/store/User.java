package game.store;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author @HackeaMesta
 */

public class User {

    private final SimpleStringProperty user;
    private final SimpleStringProperty email;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty a_paterno;
    private final SimpleStringProperty a_materno;
    private final SimpleStringProperty foto;
    private final SimpleStringProperty tipo;
    private final SimpleFloatProperty saldo;

    public User(String user, String email, String nombre, String a_paterno, String a_materno, String foto, String tipo, Float saldo) {
        this.user = new SimpleStringProperty(user);
        this.email = new SimpleStringProperty(email);
        this.nombre = new SimpleStringProperty(nombre);
        this.a_paterno = new SimpleStringProperty(a_paterno);
        this.a_materno = new SimpleStringProperty(a_materno);
        this.foto = new SimpleStringProperty(foto);
        this.tipo = new SimpleStringProperty(tipo);
        this.saldo = new SimpleFloatProperty(saldo);
    }

    public String getUser() {
        return user.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getA_paterno() {
        return a_paterno.get();
    }

    public String getA_materno() {
        return a_materno.get();
    }

    public String getFoto() {
        return foto.get();
    }

    public String getTipo() {
        return tipo.get();
    }

    public Float getSaldo() {
        return saldo.get();
    }

}
