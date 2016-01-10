package game.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author @HackeaMesta
 */

public class Connector {

    private Connection conn;
    private String db_host, db_user, db_pass, db_name;

    public Connector() {
        this.db_host = "localhost";
        this.db_user = "root";
        this.db_pass = "";
        this.db_name = "VIDEOJUEGOS";
    }

    public Connection open() throws ClassNotFoundException, SQLException {
        String data = "jdbc:mysql://" + db_host + "/" + db_name;
        String driver = "com.mysql.jdbc.Driver";

        Class.forName(driver);
        conn = DriverManager.getConnection(data, db_user, db_pass);

        return conn;
    }

    public Connection close() throws SQLException {
        conn.close();
        return conn = null;
    }

    public boolean execute(String query) throws SQLException {
        Statement cmd = null;
        cmd = conn.createStatement();

        int i = cmd.executeUpdate(query);
        if (i > 0) {
            return true;
        } else {
            return false;

        }
    }

    public ResultSet getData(String query) throws SQLException {
        Statement cmd = null;
        cmd = conn.createStatement();

        ResultSet res = cmd.executeQuery(query);
        return res;
    }
}
