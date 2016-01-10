package game.store;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author @HackeaMesta
 */
public class MyProfileController implements Initializable {

    private User_Standar userFunction;
    private Users userAction;

    @FXML
    private Label notification;
    @FXML
    private Label nickname;
    @FXML
    private TextField email;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidoP;
    @FXML
    private TextField apellidoM;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;
    @FXML
    private Label saldo;
    @FXML
    private TextField saldo_codigo;
    @FXML
    private Label notification_saldo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nickname.setText("Mi perfil: " + session.nickname);
        userFunction = new User_Standar(session.nickname);
        try {
            for (String[] data : userFunction.myData()) {
                email.setText(data[1]);
                nombre.setText(data[3]);
                apellidoP.setText(data[4]);
                apellidoM.setText(data[5]);
                saldo.setText("Mi Saldo: $"+data[8]);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void btn_saldo_click(ActionEvent evt) throws SQLException, ClassNotFoundException {
        String codigo = saldo_codigo.getText();
        if (userFunction.addCash(codigo, notification_saldo)) {
            for (String[] data : userFunction.myData()) {
                saldo.setText("Mi Saldo: $"+data[8]);
            }
            saldo_codigo.clear();
            notification_saldo.setText("Tu saldo se recargo correctamente");
        }
    }

    public void btn_update_click(ActionEvent Sender) throws ClassNotFoundException, SQLException {

        String nombre_ = nombre.getText();
        String email_ = email.getText();
        String apellido_m = apellidoM.getText();
        String apellido_p = apellidoP.getText();
        String foto = null;
        String pass1 = password.getText();
        String pass2 = password2.getText();

        userAction = new Users(session.nickname, email_, pass1, pass2, nombre_, apellido_p, apellido_m, foto, notification);

        if (userAction.updateUser()) {
            notification.setText("Tu cuenta se actualizo correctamnte");
        } else {
            notification.setText("Ocurrio un error, vuelve a intentar");
        }
    }

    public void btn_delete_click(ActionEvent Sender) throws ClassNotFoundException, SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar acción");
        alert.setHeaderText("Estas a punto de eliminar tu cuenta");
        alert.setContentText("¿Desea eliminar tu cuenta permanentemente?");

        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.get() == ButtonType.OK) {
            if (userAction.deleteUser()) {
                notification.setText("Tu cuenta se elimino correctamente");
            } else {
                notification.setText("Ocurrio un error, vuelve a intentar");
            }
        }
    }
}
