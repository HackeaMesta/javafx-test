package game.store;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author @HackeaMesta
 */
public class SignupController implements Initializable {

    private Users user;

    @FXML
    private TextField nickname;
    @FXML
    private TextField email;
    @FXML
    private TextField apellidoM;
    @FXML
    private TextField apellidoP;
    @FXML
    private TextField nombre;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;
    @FXML
    private Label notification;
    @FXML
    private Button btn_login;
    @FXML
    private CheckBox tos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void btn_signup_click(ActionEvent sender) throws ClassNotFoundException, SQLException {
        String nombre_ = nombre.getText();
        String email_ = email.getText();
        String apellido_m = apellidoM.getText();
        String apellido_p = apellidoP.getText();
        String foto = null;
        String nickname_ = nickname.getText();
        String pass1 = password.getText();
        String pass2 = password2.getText();

        user = new Users(nickname_, email_, pass1, pass2, nombre_, apellido_p, apellido_m, foto, notification);

        if (user.validateUser()) {
            if (tos.isSelected()) {
                if (user.createUser()) {
                    notification.setStyle("-fx-color: green");
                    notification.setText("Su cuenta se creo correctamente");
                } else {
                    notification.setText("Ocurrio un error, vuelve a intentarlo.");
                }
            } else {
                notification.setText("Debes aceptar los Terminos y Condiciones");
            }
        }
    }

    public void btn_login_click(ActionEvent sender) throws IOException {
        //cierra la ventana actual
        Stage hideThis = (Stage) btn_login.getScene().getWindow();
        hideThis.close();

        //Abrir Main
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Iniciar Sesión");
        stage.show();
    }
}
