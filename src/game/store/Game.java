package game.store;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author @HackeaMesta
 */
public class Game {
    private final SimpleIntegerProperty id_videojuego;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty descripcion;
    private final SimpleStringProperty editor;
    private final SimpleFloatProperty precio;
    private final SimpleIntegerProperty descargas;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty categoria;

    public Game(Integer id_videojuego, String nombre, String descripcion, String editor, Float precio, Integer descargas, String fecha, String categoria) {
        this.id_videojuego = new SimpleIntegerProperty(id_videojuego);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.editor = new SimpleStringProperty(editor);
        this.precio = new SimpleFloatProperty(precio);
        this.descargas = new SimpleIntegerProperty(descargas);
        this.fecha = new SimpleStringProperty(fecha);
        this.categoria = new SimpleStringProperty(categoria);
    }

    public Integer getId_videojuego() {
        return id_videojuego.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public String getEditor() {
        return editor.get();
    }

    public Float getPrecio() {
        return precio.get();
    }

    public Integer getDescargas() {
        return descargas.get();
    }

    public String getFecha() {
        return fecha.get();
    }

    public String getCategoria() {
        return categoria.get();
    }    
}
