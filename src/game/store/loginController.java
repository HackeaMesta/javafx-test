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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author @HackeaMesta
 */
public class loginController implements Initializable {

    private session sess;

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Label notification;
    @FXML
    private Button login_btn;
    @FXML
    private Button signup_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sess = new session();
    }

    @FXML
    private void login_btn_click(ActionEvent sender) throws ClassNotFoundException, SQLException, IOException {
        String nickname = user.getText();
        String pass = password.getText();

        //Valida la sesion
        if (sess.login(nickname, pass)) {
            //cierra la ventana actual
            Stage hideThis = (Stage) login_btn.getScene().getWindow();
            hideThis.close();

            //Abrir Main
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent main_panel = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(main_panel));
            stage.setTitle("Game Store - It's not a Steam Parody");
            stage.show();

        } else {
            notification.setText("Usuario o contraseña incorrectos");
        }
    }

    @FXML
    private void signup_btn_click(ActionEvent sender) throws IOException {
        Stage hideThis = (Stage) signup_btn.getScene().getWindow();
        hideThis.close();

        //Abrir Registro
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
        Parent main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Registrarme!");
        stage.show();
    }
}
