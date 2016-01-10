package game.store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Label;

/**
 *
 * @author @HackeaMesta
 */
public class User_Standar {
    private String nickname;
    
    private Connector conn;

    public User_Standar(String nickname) {
        this.nickname = nickname;
        this.conn = new Connector();
    }
    
    public ArrayList myGames() throws ClassNotFoundException, SQLException {
        conn.open();
        ArrayList<String[]> myGames = new ArrayList<String[]>();
        
        ResultSet data = conn.getData("SELECT * FROM HISTORIAL_COMPRAS WHERE NICKNAME = '"+this.nickname+"';");
        
            while (data.next()) {
                String[] data_data = new String[3];
                data_data[0] = data.getString(1);
                data_data[1] = data.getString(2);
                data_data[2] = data.getString(3);

                myGames.add(data_data);
            }
        conn.close();
        return myGames;        
    }
    
    public ArrayList<String[]> myData() throws SQLException, ClassNotFoundException {
        ArrayList<String[]> user_data = new ArrayList<String[]>();
        conn.open();

        if (nickname != "") {
            ResultSet data = conn.getData("SELECT * FROM USUARIOS WHERE NICKNAME = '" + nickname + "';");

            while (data.next()) {
                String[] data_data = new String[9];
                data_data[0] = data.getString(1);
                data_data[1] = data.getString(2);
                data_data[2] = data.getString(3);
                data_data[3] = data.getString(4);
                data_data[4] = data.getString(5);
                data_data[5] = data.getString(6);
                data_data[6] = data.getString(7);
                data_data[7] = data.getString(8);
                data_data[8] = data.getString(9);

                user_data.add(data_data);
            }
        }

        conn.close();

        return user_data;
    }
    
    public boolean addCash(String codigo, Label notification) throws ClassNotFoundException, SQLException {
        boolean status = false;
        conn.open();
        
        Integer total = 0;
        Float cantidad = Float.parseFloat("0");
        ResultSet codigos = conn.getData("SELECT * FROM codigos WHERE codigo = '"+codigo+"';");
        while (codigos.next()) {
            total++;
            cantidad += codigos.getFloat(2);
        }
        
        if (total == 1) {
            ResultSet saldoActual = conn.getData("SELECT saldo FROM USUARIOS WHERE NICKNAME = '"+session.nickname+"';");
            while (saldoActual.next()) {
                cantidad += saldoActual.getFloat(1);
            }
            if (conn.execute("UPDATE USUARIOS SET saldo = "+cantidad.toString()+" WHERE NICKNAME = '"+session.nickname+"';")) {
                if (conn.execute("DELETE FROM codigos WHERE codigo = '"+codigo+"';")) {
                    status = true;
                }
            }
        } else {
            notification.setText("El código ingresado es invalido.");
            status = false;
        }
        
        return status;
    }
}
