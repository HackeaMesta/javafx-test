package game.store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author @HackeaMesta
 */
public class User_Tester extends User_Standar {

    private String nickname;
    private Connector conn;

    public User_Tester(String nickname) {
        super(nickname);
        this.conn = new Connector();
    }

    public boolean createGame(String titulo, String descripcion, Float costo, String categoria, ArrayList<String[]> imagenes) throws ClassNotFoundException, SQLException {
        boolean status = false;
        conn.open();

        Date fechaActual = new Date();

        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM--dd");

        Integer id = 0;
        ResultSet id_query = conn.getData("SELECT MAX(`ID_VIDEOJUEGO`) FROM VIDEOJUEGOS");
        while (id_query.next()) {
            id = id_query.getInt(1);
        }
        id++;

        if (conn.execute("INSERT INTO VIDEOJUEGOS "
                + "(ID_VIDEOJUEGO, TITULO, DESCRIPCION, COSTO, ID_DESARROLLADOR, CATEGORIA, FECHA, STATUS, DESCARGAS) "
                + "VALUES (" + id.toString() + ", '" + titulo + "', '" + descripcion + "', " + costo.toString() + ", '" + session.nickname + "', '" + categoria + "', '" + formatoFecha.format(fechaActual) + "', 0, 0);")) {
            //Si se agregan imagenes...
            if (imagenes.isEmpty()) {
                status = true;
            } else {
                String query = "INSERT INTO videojuego_meta (id_videojuego, archivo, tipo) VALUES";
                for (String[] imagen : imagenes) {
                    query += " (" + id + ", '" + imagen[0] + "', 0),";
                }
                query = query.substring(0, query.length() - 1) + ";";
                if (conn.execute(query)) {
                    status = true;
                } else {
                    status = false;
                }
            }
        } else {
            status = false;
        }
        conn.close();
        return status;
    }

    public void deleteGame(Integer id_videojuego) throws ClassNotFoundException, SQLException {
        conn.open();

        if (conn.execute("DELETE FROM VIDEOJUEGOS WHERE ID_VIDEOJUEGO = " + id_videojuego)) {

        }
    }

    public ArrayList showMyGames() throws ClassNotFoundException, SQLException {
        conn.open();
        ArrayList<String[]> myGames = new ArrayList<String[]>();

        ResultSet data = conn.getData("SELECT * FROM VIDEOJUEGOS WHERE ID_DESARROLLADOR = '" + this.nickname + "';");

        while (data.next()) {
            String[] data_data = new String[3];
            data_data[0] = data.getString(1);
            data_data[1] = data.getString(2);
            data_data[2] = data.getString(3);
            data_data[3] = data.getString(4);
            data_data[4] = data.getString(5);
            data_data[5] = data.getString(6);
            data_data[6] = data.getString(7);
            data_data[7] = data.getString(8);
            data_data[8] = data.getString(9);

            myGames.add(data_data);
        }
        conn.close();
        return myGames;
    }

}
