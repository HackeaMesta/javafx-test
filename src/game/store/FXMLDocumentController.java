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
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author @HackeaMesta
 */

public class FXMLDocumentController implements Initializable {

    private Store compra;
    private Connector conn;

    @FXML
    private ImageView top;
    @FXML
    private TableView<Game> top_games;
    @FXML
    private TableColumn<Game, String> nombre;
    @FXML
    private TableColumn<Game, String> descripcion;
    @FXML
    private TableColumn<Game, Float> precio;
    @FXML
    private Menu admin_menu;
    public ObservableList<Game> games;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //inicia una nueva session de compra
        /*
        For Testing...
        
        session.nickname = "hackeamesta";
        session.tipo_usuario = "0";
        Testing
        */
        compra = new Store(session.nickname);
        

        nombre.setCellValueFactory(new PropertyValueFactory<Game, String>("nombre"));
        descripcion.setCellValueFactory(new PropertyValueFactory<Game, String>("descripcion"));
        precio.setCellValueFactory(new PropertyValueFactory<Game, Float>("precio"));

        try {
            getGames();
            compra.topVendidos(top);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (session.tipo_usuario.equals("0")) {
            admin_menu.setVisible(true);
        } else {
            admin_menu.setVisible(false);
        }
    }

    public void handle(MouseEvent evt) {//Listener doble click
        if (evt.getClickCount() == 2) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    //System.out.println(top_games.getSelectionModel().getSelectedItem().getId_videojuego().toString());
                    openWindow(top_games.getSelectionModel().getSelectedItem().getId_videojuego(), top_games.getSelectionModel().getSelectedItem().getNombre());
                    return null;
                }
            };

            new Thread(task).start();
        }
    }

    public void openWindow(Integer id_vj, String titulo_w) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    compra.id_videojuego = id_vj;
                    //Abrir nueva ventana con info del juego
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowGame.fxml"));
                    Parent main_panel;
                    main_panel = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(main_panel));
                    stage.setTitle(titulo_w);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void openAdmin(ActionEvent evt) throws IOException {
        //Abrir nueva ventana con info del juego
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent main_panel;
        main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Panel de Administración");
        stage.show();
    }

    public void openMyProfile(ActionEvent evt) throws IOException {
        //Abrir nueva ventana con info del juego
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent main_panel;
        main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Mi Perfil");
        stage.show();
    }
    
    public void openMyGames(ActionEvent evt) throws IOException {
        //Abrir nueva ventana con info del juego
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("myGames.fxml"));
        Parent main_panel;
        main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Juegos en mi lista");
        stage.show();
    }
    
    public void openAddGame(ActionEvent evt) throws IOException {
        //Abrir nueva ventana con info del juego
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addGame.fxml"));
        Parent main_panel;
        main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Subir nuevo Video Juego!");
        stage.show();
    }

    public void getGames() throws ClassNotFoundException {
        try {
            conn = new Connector();
            conn.open();
            games = FXCollections.observableArrayList();
            ResultSet gamesInfo = conn.getData("SELECT * FROM VIDEOJUEGOS ORDER BY FECHA DESC LIMIT 0, 10;");

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

    public void openCartWindow(ActionEvent event) throws IOException {
        //Abrir nueva ventana con info del juego
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shop.fxml"));
        Parent main_panel;
        main_panel = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(main_panel));
        stage.setTitle("Detalle de Compra");
        stage.show();
    }

    public void exitMenu(ActionEvent evt) {
        System.exit(0);
    }
}
