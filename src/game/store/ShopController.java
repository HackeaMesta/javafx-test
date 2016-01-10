package game.store;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.lang.Float;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author @HackeaMesta
 */
public class ShopController implements Initializable {

    private Store compra;
    private User_Standar userFunction;
    public ObservableList<Game> gamesInShop;

    @FXML
    private TableView TablaCompras;
    @FXML
    private TableColumn nombre;
    @FXML
    private TableColumn descripcion;
    @FXML
    private TableColumn precio;

    @FXML
    private Label user;
    @FXML
    private Label total;
    @FXML
    private Label notification;
    @FXML
    private Label saldo;
    
    private ArrayList<String[]> compra_bak;
    private Float total_bak;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.compra_bak = Store.compra;
        this.total_bak = Store.total;
        
        compra = new Store(session.nickname);
        Store.compra = this.compra_bak;
        Store.total = this.total_bak;
        
        nombre.setCellValueFactory(new PropertyValueFactory<Game, String>("nombre"));
        descripcion.setCellValueFactory(new PropertyValueFactory<Game, String>("descripcion"));
        precio.setCellValueFactory(new PropertyValueFactory<Game, Float>("precio"));
        try {
            getGames();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShopController.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.setText(session.nickname);
        total.setText("Total: $" + Store.total.toString());

        //Obtiene el saldo
        userFunction = new User_Standar(session.nickname);
        try {
            for (String[] data : userFunction.myData()) {
                saldo.setText("Mi saldo: $" + data[8]);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShopController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShopController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getGames() throws ClassNotFoundException {
        gamesInShop = FXCollections.observableArrayList();

        if (Store.compra != null) {
            for (String[] data : Store.compra) {
                Game game = new Game(Integer.parseInt(data[0]), data[1], data[2], data[5], Float.parseFloat(data[4]), Integer.parseInt(data[3]), data[7], data[8]);
                gamesInShop.add(game);
            }
        } else {
            gamesInShop = null;
        }
        TablaCompras.setItems(gamesInShop);
    }

    public void pagar_btn_click(ActionEvent Sender) throws ClassNotFoundException, SQLException {
        if (compra.endBuy(notification)) {
            /*
            Store.compra.clear();
            Store.total = Float.parseFloat("0");
            */
            notification.setText("Tu compra se realizo correctamente!");
            getGames();
            total.setText("Total: $" + Store.total.toString());
        }
    }

    public void cancelar_btn_click(ActionEvent Sender) throws ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar acción");
        alert.setHeaderText("Estas a punto de eliminar tu lista de compras");
        alert.setContentText("¿Desea cancelar tu compra?");

        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.get() == ButtonType.OK) {
            //Store.compra = null;
            Store.compra.clear();
            Store.total = Float.parseFloat("0");
            getGames();
            total.setText("Total: $" + Store.total.toString());
        }
    }
}
