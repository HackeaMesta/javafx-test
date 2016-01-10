package game.store;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author @HackeaMesta
 */

public class Ventas {
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty usuario;
    private final SimpleStringProperty total;
    private final SimpleIntegerProperty productos;
    
    private Connector conn;
    
    public Ventas(String fecha, String usuario, String total, Integer productos) {
        this.fecha = new SimpleStringProperty(fecha);
        this.usuario = new SimpleStringProperty(usuario);
        this.total = new SimpleStringProperty(total);
        this.productos = new SimpleIntegerProperty(productos);
        this.conn = new Connector();
    }

    public String getFecha() {
        return fecha.get();
    }

    public String getUsuario() {
        return usuario.get();
    }

    public String getTotal() {
        return total.get();
    }

    public Integer getProductos() {
        return productos.get();
    }
}
