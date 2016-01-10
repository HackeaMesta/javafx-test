package game.store;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author @HackeaMesta
 */
public class sliderShow {

    private ImageView slider;
    private Timer timer;
    private ArrayList<String> imagenes;

    sliderShow(ImageView slider, ArrayList<String> imagenes, Integer time) {
        this.slider = slider;
        this.imagenes = imagenes;
        this.timer = new Timer();
        this.imagenes = new ArrayList<String>();

        TimerTask task = new TimerTask() {
            int id = 0;

            @Override
            public void run() {

                if (id >= imagenes.size()) {
                    id = 0;
                }

                File file = new File(imagenes.get(id));
                String imagen = imagenes.get(id);
                Image image = new Image(file.toURI().toString());
                slider.setImage(image);
                slider.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent me) {
                        if (me.getClickCount() == 2) {
                            try {
                                openWindow(imagen);
                            } catch (IOException ex) {
                                Logger.getLogger(sliderShow.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(sliderShow.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(sliderShow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                );
                id++;
            }
        };

        //Tiempo en el que tarda el slider
        timer.schedule(task, 0, time);
    }

    public void openWindow(String imagen) throws IOException, SQLException, ClassNotFoundException {
        Connector conn = new Connector();
        conn.open();
        Store compra = new Store(session.nickname);

        ArrayList<String> datos = new ArrayList<String>();
        ResultSet datosImagen = conn.getData("SELECT ID_VIDEOJUEGO, TITULO FROM VIDEOJUEGOS WHERE ID_VIDEOJUEGO = (SELECT id_videojuego FROM videojuego_meta WHERE archivo = '" + imagen + "');");
        while (datosImagen.next()) {
            datos.add(datosImagen.getString(1));
            datos.add(datosImagen.getString(2));
        }

        conn.close();
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    compra.id_videojuego = Integer.parseInt(datos.get(0));
                    //Abrir nueva ventana con info del juego
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowGame.fxml"));
                    Parent main_panel;
                    main_panel = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(main_panel));
                    stage.setTitle(datos.get(1));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
