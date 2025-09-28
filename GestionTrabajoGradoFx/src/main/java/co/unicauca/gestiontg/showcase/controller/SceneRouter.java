package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.controller.AuthController;
import java.io.IOException;
import javafx.stage.Stage;

/**
 *
 * @author kthn1
 */
public interface SceneRouter {

    void goToStudentModule(AuthController authController) throws IOException;

    void goToTeacherModule(AuthController authController, co.unicauca.gestiontg.controller.FormatoAController formatoAController) throws IOException;

    void goToCoordinadorModule(AuthController authController, co.unicauca.gestiontg.controller.FormatoAController formatoAController) throws IOException;

    void goToRegister(AuthController authController) throws IOException;

    Stage getPrimaryStage();
}
