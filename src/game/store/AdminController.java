package game.store;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author @HackeaMesta
 */

public class AdminController implements Initializable {

    private User_Admin adminFunctions;

    @FXML
    private LineChart<String, Number> ventas_lineChart;
    @FXML
    private PieChart topGames_pieChart;

    @FXML
    private TableView<Game> tabla_juegos;
    @FXML
    private TableColumn<Game, String> nombre;
    @FXML
    private TableColumn<Game, String> editor;
    @FXML
    private TableColumn<Game, Float> fecha;
    @FXML
    private TableView<User> tabla_usuarios;
    @FXML
    private TableColumn<User, String> user;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TextField codigo;
    @FXML
    private TextField monto;
    @FXML
    private Label notification;

    public ObservableList<Game> games;
    public ObservableList<User> users;

    private Connector conn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminFunctions = new User_Admin(session.nickname);
        //Inicializa las tablas
        nombre.setCellValueFactory(new PropertyValueFactory<Game, String>("nombre"));
        editor.setCellValueFactory(new PropertyValueFactory<Game, String>("editor"));
        fecha.setCellValueFactory(new PropertyValueFactory<Game, Float>("fecha"));

        user.setCellValueFactory(new PropertyValueFactory<User, String>("user"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("nombre"));

        //Agrega las estadisticas
        try {
            getGames();
            getUsers();
            adminFunctions.estadisticas(ventas_lineChart, topGames_pieChart);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openVentas(ActionEvent evt) throws IOException {
        //Abrir nueva ventana con info del juego
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("comprasView.fxml"));
        Parent main_panel;
        main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Detalles de Ventas por periodo");
        stage.show();
    }

    public void handleGames(MouseEvent evt) {//Listener doble click
        if (evt.getClickCount() == 2) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    //System.out.println(top_games.getSelectionModel().getSelectedItem().getId_videojuego().toString());
                    openGame(tabla_juegos.getSelectionModel().getSelectedItem().getId_videojuego(), tabla_juegos.getSelectionModel().getSelectedItem().getNombre());
                    return null;
                }
            };

            new Thread(task).start();
        }
    }

    public void openGame(Integer id, String titulo) {
        Store compra = new Store(session.nickname);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    compra.id_videojuego = id;
                    //Abrir nueva ventana con info del juego
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowGame.fxml"));
                    Parent main_panel;
                    main_panel = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(main_panel));
                    stage.setTitle(titulo);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void handleUsers(MouseEvent evt) {//Listener doble click
        if (evt.getClickCount() == 2) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    openUser(tabla_usuarios.getSelectionModel().getSelectedItem().getUser().toString());
                    return null;
                }
            };

            new Thread(task).start();
        }
    }

    public void openUser(String username) throws IOException {
        session.perfil = username;
        //Abrir nueva ventana con info del juego
        FXMLLoader newWin = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent main_win;
        main_win = (Parent) newWin.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_win));
        stage.show();
    }

    public void getUsers() throws ClassNotFoundException {
        try {
            conn = new Connector();
            conn.open();
            users = FXCollections.observableArrayList();
            ResultSet usersInfo = conn.getData("SELECT * FROM USUARIOS;");

            while (usersInfo.next()) {
                User user_ = new User(usersInfo.getString(1), usersInfo.getString(2),
                        usersInfo.getString(4), usersInfo.getString(5),
                        usersInfo.getString(6), usersInfo.getString(7),
                        usersInfo.getString(8), usersInfo.getFloat(9));
                users.add(user_);
            }

            tabla_usuarios.setItems(users);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getGames() throws ClassNotFoundException {
        try {
            conn = new Connector();
            conn.open();
            games = FXCollections.observableArrayList();
            ResultSet gamesInfo = conn.getData("SELECT * FROM VIDEOJUEGOS ORDER BY FECHA DESC;");

            while (gamesInfo.next()) {
                Game game = new Game(gamesInfo.getInt(1), gamesInfo.getString(2),
                        gamesInfo.getString(3), gamesInfo.getString(6),
                        gamesInfo.getFloat(5), gamesInfo.getInt(4),
                        gamesInfo.getString(8), gamesInfo.getString(9));
                games.add(game);
            }

            tabla_juegos.setItems(games);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void code_btn(ActionEvent evt) throws ClassNotFoundException, SQLException {
        String code = codigo.getText();
        Float cantidad = Float.parseFloat(monto.getText());
        if (adminFunctions.createCode(code, cantidad)) {
            notification.setText("El codigo se creo correctamente");
        } else {
            notification.setText("Ocurrio un error intenta nuevamente!");
        }
    }
}
