package game.store;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author @hackeamesta
 */

public class ComprasViewController implements Initializable {
    @FXML
    private TextField desde;
    @FXML
    private TextField hasta;
    @FXML
    private TableView<Ventas> compras;
    @FXML
    private TableColumn<Ventas, String> fecha;
    @FXML
    private TableColumn<Ventas, String> usuario;
    @FXML
    private TableColumn<Ventas, String> total_col;
    @FXML
    private TableColumn<Ventas, Integer> productos;
    @FXML
    private Label total;
    
    public Float total_compra;
    public ObservableList<Ventas> ventas_list;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        total_compra = Float.parseFloat("0");
        
        fecha.setCellValueFactory(new PropertyValueFactory<Ventas, String>("fecha"));
        usuario.setCellValueFactory(new PropertyValueFactory<Ventas, String>("usuario"));
        total_col.setCellValueFactory(new PropertyValueFactory<Ventas, String>("total"));
        productos.setCellValueFactory(new PropertyValueFactory<Ventas, Integer>("productos"));
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Integer mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
        desde.setText(Calendar.getInstance().get(Calendar.YEAR) + "-" + mes + "-" + calendar.getTime().getDate());
        
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
        hasta.setText(Calendar.getInstance().get(Calendar.YEAR) + "-" + mes + "-" + calendar.getTime().getDate());        
        
        try {
            filtrar(desde.getText(), hasta.getText());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComprasViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ComprasViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void filtar_btn(ActionEvent evt) throws ClassNotFoundException, SQLException {
        if (!desde.getText().isEmpty() && !hasta.getText().isEmpty()) {
            filtrar(desde.getText(), hasta.getText());
        } else {
            total.setText("Ingresa una fecha");
        }
    }
    
    private void filtrar(String fecha_inicial, String fecha_final) throws ClassNotFoundException, SQLException {
        Connector conn = new Connector();
        
        conn.open();
        ventas_list = FXCollections.observableArrayList();
            ResultSet ventasInfo = conn.getData("SELECT fecha, nickname_usuario, total, "
                    + "(SELECT COUNT(*) FROM detalle_ventas WHERE id_venta = Ventas.id_venta) "
                    + "FROM Ventas WHERE fecha >= '"+fecha_inicial+"' AND fecha <= '"+fecha_final+"';");

            while (ventasInfo.next()) {
                Ventas venta_ = new Ventas(ventasInfo.getString(1), ventasInfo.getString(2),
                        ventasInfo.getString(3), ventasInfo.getInt(4));
                ventas_list.add(venta_);
                total_compra += Float.parseFloat(ventasInfo.getString(3));
            }

            compras.setItems(ventas_list);
            total.setText("Total: MXN "+total_compra.toString());
        conn.close();
    }
    
}
