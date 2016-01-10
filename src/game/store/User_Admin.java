package game.store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author @HackeaMesta
 */
public class User_Admin extends User_Tester {

    private String nickname;
    private Connector conn;

    public User_Admin(String nickname) {
        super(nickname);
        this.conn = new Connector();
    }

    public void estadisticas(LineChart lineChart, PieChart pieChart) throws ClassNotFoundException, SQLException {
        conn.open();
        lineChart.getData().clear();
        ArrayList<String[]> data = new ArrayList<String[]>();

        /*
        Estadisticas dde ventas
         */
        //Obtiene fechas a calcular
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> fechas = new ArrayList<String>();
        
        calendar.add(Calendar.DATE, 0);
        Integer mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
        fechas.add(Calendar.getInstance().get(Calendar.YEAR) + "-" + mes + "-" + calendar.getTime().getDate());
        calendar = Calendar.getInstance();
        
        for (int i = 1; i < 6; i++) {
            calendar.add(Calendar.DATE, -i);
            mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
            fechas.add(Calendar.getInstance().get(Calendar.YEAR) + "-" + mes + "-" + calendar.getTime().getDate());
            calendar = Calendar.getInstance();
        }

        for (int i = 5; i >= 0; i--) {
            ResultSet dato = conn.getData("SELECT count(*) FROM Ventas WHERE fecha = '" + fechas.get(i) + "';");
            while (dato.next()) {
                String[] get_data = new String[2];
                get_data[0] = fechas.get(i);
                get_data[1] = dato.getString(1);

                data.add(get_data);
            }
        }

        XYChart.Series<String, Number> ventas = new XYChart.Series<String, Number>();

        for (int i = 0; i < 6; i++) {
            for (String[] datos : data) {
                ventas.getData().add(new XYChart.Data<String, Number>(datos[0].toString(), Integer.parseInt(datos[1])));
            }
        }
        
        ventas.setName("Ventas");
        lineChart.getData().add(ventas);
        
        /*
        Top Games mas descargados
        */
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        
        ResultSet top_games = conn.getData("SELECT TITULO, DESCARGAS FROM VIDEOJUEGOS ORDER BY DESCARGAS DESC LIMIT 0,5;");
        while(top_games.next()) {
            String titulo = top_games.getString(1).substring(0, 13) + "... ("+top_games.getString(2)+")"; 
            list.add(new PieChart.Data(titulo, top_games.getInt(2)));
        }
        pieChart.setData(list);
    }

    public ArrayList showAllGames() throws ClassNotFoundException, SQLException {
        conn.open();
        ArrayList<String[]> allGames = new ArrayList<String[]>();

        ResultSet data = conn.getData("SELECT * FROM VIDEOJUEGOS WHERE STATUS = 0;");

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

            allGames.add(data_data);
        }
        conn.close();
        return allGames;
    }

    public void approveGame(Integer id_videojuego) throws ClassNotFoundException, SQLException {
        conn.open();
        if (conn.execute("UPDATE VIDEOJUEGOS SET STATUS = 1 WHERE ID_VIDEOJUEGO = '" + id_videojuego + "';")) {

        }
    }

    public void refuseGame(Integer id_videojuego) throws ClassNotFoundException, SQLException {
        conn.open();
        if (conn.execute("UPDATE VIDEOJUEGOS SET STATUS = 2 WHERE ID_VIDEOJUEGO = '" + id_videojuego + "';")) {

        }
    }
    
    public void ventasDetails() throws ClassNotFoundException, SQLException {
        conn.open();
        
    }
}
