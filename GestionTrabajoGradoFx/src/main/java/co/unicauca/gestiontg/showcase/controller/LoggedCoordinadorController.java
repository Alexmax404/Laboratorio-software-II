package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.factory.FormatoAControllerFactory;
import co.unicauca.gestiontg.showcase.router.SceneRouter;
import java.io.IOException;
import java.sql.SQLException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LoggedCoordinadorController {

    private FormatoAController formatoAController;

    private AuthController authController;

    private FormatoAControllerFactory formatoFactory;

    private SceneRouter router;

    public void setController(AuthController authController) {
        this.authController = authController;
        if (authController.getUsuarioLogueado() != null) {
            cargarDatosCoordinador();
        }
    }

    public void setFormatoAController(FormatoAController formatoAController) {
        this.formatoAController = formatoAController;
    }

    public void setFormatoFactory(FormatoAControllerFactory factory) {
        this.formatoFactory = factory;
    }

    public void setRouter(SceneRouter router) {
        this.router = router;
    }

    @FXML
    private Button btnEditarFormatoA;

    @FXML
    private Button btnVerEstadoDeFormatoA;

    @FXML
    private ImageView fadingImage;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblRol;

    @FXML
    private Hyperlink linkExit;

    @FXML
    private Pane pnDatos;

    @FXML
    private Pane pnDatos1;

    @FXML
    void handleClickPane(MouseEvent event) {
        pnDatos.requestFocus();
    }

    @FXML
    void handlePress(MouseEvent event) {
        Button btn = (Button) event.getSource(); // Obtiene el botón que lanzó el evento
        btn.setCursor(Cursor.HAND); // 👈 Manita al pasar por encima
        btn.setStyle(
                "-fx-background-color: #dcdcdc;"
                + "-fx-translate-y: 1px;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 10;"
                + "-fx-background-radius: 10;"
        );
    }

    @FXML
    void handleRelease(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setCursor(Cursor.HAND); // 👈 Manita se mantiene
        btn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-translate-y: 0;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 10;"
                + "-fx-background-radius: 10;"
        );
    }

    @FXML
    void switchToMainMenu(ActionEvent event) throws IOException {
        authController.logout();
        if (router != null) {
            router.goToMainMenu(authController, formatoFactory);
        }
    }

    @FXML
    void switchToVerFormatosA(ActionEvent event) throws IOException, SQLException {
        router.goToListaDeEstados(authController, formatoAController);

    }

    public void cargarDatosCoordinador() {
        if (authController != null) {
            var usuario = authController.getUsuarioLogueado();
            if (usuario != null) {
                lblName.setText(usuario.getNombreCompleto());   // 👈 aquí pones nombres
                lblCorreo.setText(usuario.getCorreo());
            }
        }
    }

    @FXML
    public void initialize() {

        FadeTransition fade = new FadeTransition(Duration.seconds(1), fadingImage);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        fade.setOnFinished(event -> {
            // Cuando termine la animación, mandar el ImageView al fondo
            fadingImage.toBack();
        });

        fade.play();
    }
}
