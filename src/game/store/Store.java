package game.store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 *
 * @author @HackeaMesta
 */

public class Store {

    private String user_name;
    public static ArrayList<String[]> compra;
    public static Float total;
    public static int id_videojuego;
    private sliderShow slider;

    private Connector conn;

    public Store(String user_name) {
        /*
        compra.addGame(2);
        compra.addGame(5);
        compra.endBuy();
         */
        this.user_name = user_name;
        this.compra = new ArrayList<String[]>();
        this.total = Float.parseFloat("0");
        this.conn = new Connector();
    }

    public void addGame(Integer id_videojuego) throws ClassNotFoundException, SQLException {
        /*
        Agrega un juego al "arreglo" de la lista
        de compras total
         */
        conn.open();
        ResultSet videojuego = conn.getData("SELECT * FROM VIDEOJUEGOS WHERE ID_VIDEOJUEGO = " + id_videojuego.toString() + ";");

        while (videojuego.next()) {
            String[] data_videojuego = new String[9];
            data_videojuego[0] = videojuego.getString(1);
            data_videojuego[1] = videojuego.getString(2);
            data_videojuego[2] = videojuego.getString(3);
            data_videojuego[3] = videojuego.getString(4);
            data_videojuego[4] = videojuego.getString(5);
            data_videojuego[5] = videojuego.getString(6);
            data_videojuego[6] = videojuego.getString(7);
            data_videojuego[7] = videojuego.getString(8);
            data_videojuego[8] = videojuego.getString(9);

            this.compra.add(data_videojuego);
            this.total += Float.parseFloat(data_videojuego[4]);
        }
    }

    public void showGameData(Label titulo, Label editor, TextArea descripcion, ImageView sliderLabel, Label downloads, Label precio) throws ClassNotFoundException, SQLException {
        conn.open();
        ArrayList<String> imagenes = new ArrayList<String>();
        ResultSet game = conn.getData("SELECT * FROM VIDEOJUEGOS WHERE ID_VIDEOJUEGO = " + this.id_videojuego);

        while (game.next()) {
            titulo.setText(game.getString(2));
            editor.setText(game.getString(6));
            descripcion.setText(game.getString(3));

            //Arreglo de imagenes multimedia
            ResultSet images = conn.getData("SELECT archivo FROM videojuego_meta WHERE id_videojuego = " + this.id_videojuego + " AND tipo = 0;");
            while (images.next()) {
                imagenes.add(images.getString(1));
            }

            downloads.setText("Descargas: " + game.getString(4));
            precio.setText(game.getString(5) + " MXN");
            //this.id_videojuego = game.getInt(1);
        }

        Integer time = 5000;
        slider = new sliderShow(sliderLabel, imagenes, time);
        conn.close();
    }

    public void topVendidos(ImageView sliderLabel) throws ClassNotFoundException, SQLException {
        conn.open();
        ArrayList<String> imagenes = new ArrayList<String>();
        ResultSet games = conn.getData("SELECT archivo FROM videojuego_meta WHERE tipo = 0 AND archivo LIKE '%header%' limit 0,5;");

        while (games.next()) {
            imagenes.add(games.getString(1));
        }

        Integer time = 4000;
        slider = new sliderShow(sliderLabel, imagenes, time);
    }

    public boolean endBuy(Label notification) throws ClassNotFoundException, SQLException {
        //Fecha Actual
        Date fechaActual = new Date();

        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        boolean status = false;
        /*
        Cuando se termina la compra se registra
        en Ventas cada uno de los
        juegos
         */
        conn.open();
        if (this.compra.isEmpty()) {
            notification.setText("Agrega productos a tu compra!");
        } else {
        Float mi_saldo = Float.parseFloat("0");
        ResultSet saldo = conn.getData("SELECT saldo FROM USUARIOS WHERE NICKNAME = '" + session.nickname + "';");
        while (saldo.next()) {
            mi_saldo += saldo.getFloat(1);
        }
        if (mi_saldo >= this.total) {
            //Obtiene el id mayor
            Integer id = 0;
            ResultSet id_query = conn.getData("SELECT MAX(`id_venta`) FROM Ventas");
            while (id_query.next()) {
                id = id_query.getInt(1);
            }
            id++;

            if (conn.execute("INSERT INTO Ventas (id_venta, nickname_usuario, total, fecha) VALUES "
                    + "(" + id.toString() + ", '" + this.user_name + "', " + this.total.toString() + ", '" + formatoFecha.format(fechaActual) + " " + formatoHora.format(fechaActual) + "');")) {
                String query = "INSERT INTO detalle_ventas (id_venta, id_videojuego, precio) VALUES";
                for (String[] videojuego_data : this.compra) {
                    query += " (" + id + ", " + videojuego_data[0] + ", " + videojuego_data[4] + "),";
                }
                query = query.substring(0, query.length() - 1) + ";";

                if (conn.execute(query)) {
                    //Actualiza el saldo del usuario
                    mi_saldo -= this.total;
                    if (conn.execute("UPDATE USUARIOS SET saldo = " + mi_saldo.toString() + " WHERE NICKNAME = '" + session.nickname + "';")) {
                        // Actualiza las descargas de cada juego comprando incrementandolas
                        for (String[] videojuego_data : this.compra) {
                            Integer descargas_totales = 1;
                            ResultSet descargas = conn.getData("SELECT DESCARGAS FROM VIDEOJUEGOS WHERE ID_VIDEOJUEGO = "+videojuego_data[0]);
                            while (descargas.next()) {
                                descargas_totales += descargas.getInt(1);
                            }
                            conn.execute("UPDATE VIDEOJUEGOS SET DESCARGAS = " + descargas_totales.toString() + " WHERE ID_VIDEOJUEGO = "+videojuego_data[0]);
                        }
                        //Resetea los datos
                        this.compra.clear();
                        this.total = Float.parseFloat("0");
                        status = true;
                    } else {
                        status = false;
                    }
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } else {
            notification.setText("Tu saldo actual es insuficiente.");
            status = false;
        }
        }

        //System.out.println(query);
        return status;
    }
}
