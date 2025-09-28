package co.unicauca.gestiontg;

import co.unicauca.gestiontg.showcase.controller.MainMenuController;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.access.UsuarioRepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.factory.FormatoAControllerFactory;
import co.unicauca.gestiontg.infra.Subject;
import co.unicauca.gestiontg.service.ServicioUsuario;
import co.unicauca.gestiontg.showcase.controller.SceneRouterImpl;
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

    @Override
    public void start(Stage stage) throws IOException {
        IUsuarioRepositorio repo = new UsuarioRepositorio();
        Subject subject = new Subject();
        ServicioUsuario servicio = new ServicioUsuario(repo, subject);
        
        AuthController controller = new AuthController(servicio);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        
        MainMenuController mainController = loader.getController();
        mainController.setController(controller);
        mainController.setRouter(new SceneRouterImpl(stage));
        mainController.setFormatoFactory(new FormatoAControllerFactory());

//        controller.addObserver(mainController);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
