package game.store;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author @HackeaMesta
 */
public class MyGamesController implements Initializable {
    private Connector conn;
    
    @FXML
    private Label user;
    @FXML
    private TableView<Game> top_games;
    @FXML
    private TableColumn<Game, String> nombre;
    @FXML
    private TableColumn<Game, String> descripcion;
    @FXML
    private TableColumn<Game, Float> precio;
    public ObservableList<Game> games;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user.setText(session.nickname+": Mi lista de Juegos");
        
        nombre.setCellValueFactory(new PropertyValueFactory<Game, String>("nombre"));
        descripcion.setCellValueFactory(new PropertyValueFactory<Game, String>("descripcion"));
        precio.setCellValueFactory(new PropertyValueFactory<Game, Float>("precio"));
        
        try {
            getGames();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyGamesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void getGames() throws ClassNotFoundException {
        try {
            conn = new Connector();
            conn.open();
            games = FXCollections.observableArrayList();
            ResultSet gamesInfo = conn.getData("SELECT * FROM VIDEOJUEGOS WHERE "
                    + "ID_VIDEOJUEGO IN (SELECT id_videojuego FROM detalle_ventas WHERE "
                    + "id_venta IN (SELECT id_venta FROM Ventas WHERE nickname_usuario = '"+session.nickname+"'));");

            while (gamesInfo.next()) {
                Game game = new Game(gamesInfo.getInt(1), gamesInfo.getString(2),
                        gamesInfo.getString(3), gamesInfo.getString(6),
                        gamesInfo.getFloat(5), gamesInfo.getInt(4),
                        gamesInfo.getString(8), gamesInfo.getString(9));
                games.add(game);
            }

            top_games.setItems(games);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
