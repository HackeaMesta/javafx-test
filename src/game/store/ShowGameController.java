package game.store;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    @FXML
    private Button delete;
    
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
        
        if (session.tipo_usuario.equals("0")) {
            delete.setVisible(true);
        } else {
            delete.setVisible(false);
        }
    }
    
    public void comprar_btn_click() throws ClassNotFoundException, SQLException {
        store.addGame(this.id_videojuego);
        notification.setText("El juego se grego correctamente!");
        //store.endBuy();
    }
    
    public void delete_btn(ActionEvent evt) throws ClassNotFoundException, SQLException {
        User_Tester deleteAction = new User_Tester(session.nickname);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar acción");
        alert.setHeaderText("Estas a punto de eliminar el Videojuego");
        alert.setContentText("¿Desea eliminar el videojuego permanentemente?");

        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.get() == ButtonType.OK) {
            if (deleteAction.deleteGame(Store.id_videojuego)) {
                /*
                Stage hideThis = (Stage) delete.getScene().getWindow();
                hideThis.close();
                */
                notification.setText("El juego se elimino correctamente");
            } else {
                notification.setText("Ocurrio un error al eliminar el juego");
            }
        }
    }
}
