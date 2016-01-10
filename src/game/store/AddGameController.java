package game.store;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author @HAckeaMesta
 */
public class AddGameController implements Initializable {

    @FXML
    private TextField editor;
    @FXML
    private TextField titulo;
    @FXML
    private TextField precio;
    @FXML
    private TextField categoria;
    @FXML
    private TextArea descripcion;
    @FXML
    private Label images_label;
    @FXML
    private Label notification;

    private ArrayList<String[]> files;
    private User_Tester testerFunctions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testerFunctions = new User_Tester(session.nickname);
        files = new ArrayList<String[]>();
        editor.setText(session.nickname);
    }

    public void save_btn_click(ActionEvent evt) throws ClassNotFoundException, SQLException {
        String tituloText = titulo.getText();
        String precioText = precio.getText();
        String categoriaText = categoria.getText();
        String descripcionText = descripcion.getText();

        if (tituloText != "") {
            if (precioText != "") {
                if (descripcionText != "") {
                    if (testerFunctions.createGame(tituloText, descripcionText, Float.parseFloat(precioText), categoriaText, files)) {
                        notification.setText("Tu juego se creo correctamente!");
                        titulo.setText("");
                        precio.setText("");
                        categoria.setText("");
                        descripcion.setText("");
                        files.clear();
                        editor.setText("");
                    } else {
                        notification.setText("Ocurrio un error, intenta de nuevo");
                    }
                } else {
                    notification.setText("Introduce una descripcion");
                }
            } else {
                notification.setText("Introduce un precio");
            }
        } else {
            notification.setText("Introduce un titulo");
        }
    }

    public void upload_file(ActionEvent evt) {
        FileChooser filechoose = new FileChooser();
        filechoose.setInitialDirectory(new File("img/"));

        filechoose.getExtensionFilters().addAll(
                new ExtensionFilter("Imagenes JPEG", "*.jpg"),
                new ExtensionFilter("Imagenes PNG", "*.png")
        );

        File file = filechoose.showOpenDialog(null);

        if (file != null) {
            //files.add(file.toString());
            String[] file_data = new String[2];
            file_data[0] = file.getAbsoluteFile().toString();
            file_data[1] = file.getName().toString();
            files.add(file_data);
            /*
            System.out.println(file.getAbsoluteFile());
            System.out.println(file.getName());
             */
            String images_text = "";
            for (String[] files_data : files) {
                images_text = images_text + ", " + files_data[1];
            }
            images_label.setText(images_text);
        } else {

        }
    }
}
