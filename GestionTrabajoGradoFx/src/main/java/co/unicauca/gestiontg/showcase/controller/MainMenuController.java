package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.showcase.router.SceneRouter;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.factory.FormatoAControllerFactory;
import co.unicauca.gestiontg.showcase.utilities.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button btnIngresar;

    @FXML
    private Hyperlink linkRegistrarse;

    @FXML
    private Pane pnDatos;

    @FXML
    private Pane pnImagen;

    @FXML
    private Pane pnMainMenu;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private StackPane rootContainer;

    private AuthController authController;
    private SceneRouter router;
    private FormatoAControllerFactory formatoFactory;

    public void setController(AuthController authController) {
        this.authController = authController;
    }

    public void setRouter(SceneRouter router) {
        this.router = router;
    }

    public void setFormatoFactory(FormatoAControllerFactory factory) {
        this.formatoFactory = factory;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    void switchToLogged(ActionEvent event) throws IOException, SQLException {
        String correo = txtCorreo.getText();
        String contrasenia = txtContraseña.getText();
        boolean validar = authController.loginUser(correo, contrasenia);

        if (validarEspaciosVacios(correo, contrasenia)) {
            return;
        }

        if (validar) {
            Optional<String> rol = authController.getRolUsuario(correo);
            if (rol.get().equals("Estudiante")) {
                if (formatoFactory == null) {
                    throw new IllegalStateException("FormatoAControllerFactory no inyectada");
                }
                router.goToStudentModule(authController, formatoFactory.create());
            } else if (rol.get().equals("Docente")) {
                if (formatoFactory == null) {
                    throw new IllegalStateException("FormatoAControllerFactory no inyectada");
                }
                router.goToTeacherModule(authController, formatoFactory.create());
            } else {
                if (formatoFactory == null) {
                    throw new IllegalStateException("FormatoAControllerFactory no inyectada");
                }
                router.goToCoordinadorModule(authController, formatoFactory.create());
            }
        }
    }

    @FXML
    void switchToRegister(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/RegisterFrame.fxml"));
        Parent root = loader.load();

        RegisterFrameController registerController = loader.getController();
        registerController.setController(authController);
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleClickPane(MouseEvent event) {
        pnDatos.requestFocus();
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        AlertUtil.mostrarAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
    }

    public boolean validarEspaciosVacios(String correo, String contrasenia) {
        if (correo == null || correo.trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacios Vacíos", "Correo o contraseña no pueden estar vacío", Alert.AlertType.WARNING);
            return true;
        }
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacios Vacíos", "Correo o contraseña no pueden estar vacío", Alert.AlertType.WARNING);
            return true;
        }
        return false;
    }
}
