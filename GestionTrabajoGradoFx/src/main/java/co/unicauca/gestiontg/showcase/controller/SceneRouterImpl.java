package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.showcase.router.SceneRouter;
import co.unicauca.gestiontg.showcase.router.RegisterFrameController;
import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.service.ServicioFormatoA;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author kthn1
 */
public class SceneRouterImpl implements SceneRouter{

    private final Stage primaryStage;

    public SceneRouterImpl(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void goToStudentModule(AuthController authController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedEstudiante.fxml"));
        Parent root = loader.load();
        LoggedEstudianteController ctrl = loader.getController();
        ctrl.setController(authController);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public void goToCoordinadorModule(AuthController authController, Button btnIngresar) throws IOException{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedCoordinador.fxml"));
                    Parent root = loader.load();

                    LoggedCoordinadorController controller = loader.getController();
                    controller.setController(authController);
                    controller.setFormatoAController(new FormatoAController(new ServicioFormatoA(new FormatoARepositorio())));

                    Stage stage = (Stage) btnIngresar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();

    }
    @Override
    public void goToTeacherModule(AuthController authController, co.unicauca.gestiontg.controller.FormatoAController formatoAController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedDocente.fxml"));
        Parent root = loader.load();
        LoggedDocenteController ctrl = loader.getController();
        ctrl.setController(authController);
        if (formatoAController != null) {
            ctrl.setFormatoAController(formatoAController);
        } 
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    @Override
    public void goToCoordinadorModule(AuthController authController, co.unicauca.gestiontg.controller.FormatoAController formatoAController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedCoordinador.fxml"));
        Parent root = loader.load();
        LoggedCoordinadorController ctrl = loader.getController();
        ctrl.setController(authController);
        if (formatoAController != null) {
            ctrl.setFormatoAController(formatoAController);
        } 
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void goToRegister(AuthController authController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/RegisterFrame.fxml"));
        Parent root = loader.load();
        RegisterFrameController ctrl = loader.getController();
        ctrl.setController(authController);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
