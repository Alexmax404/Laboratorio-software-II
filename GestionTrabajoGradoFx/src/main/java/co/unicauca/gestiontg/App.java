package co.unicauca.gestiontg;

import co.unicauca.gestiontg.access.GestionUsuario;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.access.UsuarioRepositorio;
import co.unicauca.gestiontg.service.Servicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    //LuFerpas@unicauca.edu.co
    //pr@unicauca.edu.co
    //Nico020903.
        
    @Override
    public void start(Stage stage) throws IOException {


       IUsuarioRepositorio repositorio = new UsuarioRepositorio();
       Servicio servicio = new Servicio(repositorio);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
 
        Parent root = loader.load();
        MainMenuController mainController = loader.getController();
        mainController.setServicio(servicio);
        servicio.addObserver(mainController);
        
        scene = new Scene(root); 
        stage.setScene(scene);
        stage.sizeToScene(); 
        stage.setResizable(false); 
        stage.show();
    }

//    static void setRoot(String fxml) throws IOException {
//        scene.setRoot(loadFXML(fxml));
//    }
//
//    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/co/unicauca/gestiontg/" + fxml + ".fxml"));
//        return fxmlLoader.load();
//    }

    public static void main(String[] args) {
        launch();
    }

}
