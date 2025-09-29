package co.unicauca.gestiontg.showcase.router;

import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.factory.FormatoAControllerFactory;
import co.unicauca.gestiontg.showcase.controller.LoggedCoordinadorController;
import co.unicauca.gestiontg.showcase.controller.LoggedDocenteController;
import co.unicauca.gestiontg.showcase.controller.LoggedEstudianteController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kthn1
 */
public class SceneRouterImpl implements SceneRouter {

    private final Stage primaryStage;

    public SceneRouterImpl(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void goToStudentModule(AuthController authController, co.unicauca.gestiontg.controller.FormatoAController formatoAController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedEstudiante.fxml"));
        Parent root = loader.load();
        LoggedEstudianteController ctrl = loader.getController();
        ctrl.setController(authController);

        if (formatoAController != null) {
            ctrl.setFormatoAController(formatoAController);
        }

        ctrl.setFormatoFactory(new FormatoAControllerFactory());

        ctrl.setRouter(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void goToCoordinadorModule(AuthController authController, FormatoAController formatoAController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedCoordinador.fxml"));
        Parent root = loader.load();
        LoggedCoordinadorController ctrl = loader.getController();
        ctrl.setController(authController);

        if (formatoAController != null) {
            ctrl.setFormatoAController(formatoAController);
        }

        ctrl.setFormatoFactory(new FormatoAControllerFactory());

        ctrl.setRouter(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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

        ctrl.setFormatoFactory(new FormatoAControllerFactory());

        ctrl.setRouter(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
