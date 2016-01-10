package game.store;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author @HackeaMesta
 */
public class ShowGameController implements Initializable {
    private Store store;
    public Integer id_videojuego;
    
    @FXML
    private Label titulo;
    @FXML
    private Label editor;
    @FXML
    private TextArea descripcion;
    @FXML
    private ImageView slider;
    @FXML
    private Label descargas;
    @FXML
    private Label notification;
    @FXML
    private Label precio; 
    
    private ArrayList<String[]> compra;
    private Float total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.compra = Store.compra;
        this.total = Store.total;
        /*
        Respaldar los datos de la clase Store, antes de instanciar
        la clase, restaurarlos uana vez inicializada
        */
        this.id_videojuego = Store.id_videojuego;
        
        store = new Store(session.nickname);
        Store.compra = this.compra;
        Store.total = this.total;
        
        try {
            store.showGameData(titulo, editor, descripcion, slider, descargas, precio);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowGameController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShowGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void comprar_btn_click() throws ClassNotFoundException, SQLException {
        store.addGame(this.id_videojuego);
        notification.setText("El juego se grego correctamente!");
        //store.endBuy();
    }
    
}
