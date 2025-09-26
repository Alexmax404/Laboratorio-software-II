package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.infra.Observer;
import co.unicauca.gestiontg.service.ServicioFormatoA;
import co.unicauca.gestiontg.showcase.utilities.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuController implements Observer {

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

    public void setController(AuthController authController) {
        this.authController = authController;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    void switchToLogged(ActionEvent event) throws IOException, SQLException {
        String correo = txtCorreo.getText();
        String contrasenia = txtContraseña.getText();

        // Poner alertas de que está vacio
        boolean validar = authController.loginUser(correo, contrasenia);

        if (espaciosVacios() == false) {
            if (validar) {
                Optional<String> rol = authController.getRolUsuario(correo);
                if (rol.get().equals("Estudiante")) {
                    AlertUtil.mostrarAlerta("Bienvenido!", "Dirigiendose al modulo Estudiante", Alert.AlertType.INFORMATION);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedEstudiante.fxml"));
                    Parent root = loader.load();

                    LoggedEstudianteController estudiante = loader.getController();
                    estudiante.setController(authController);

                    Stage stage = (Stage) btnIngresar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    AlertUtil.mostrarAlerta("Bienvenido!", "Dirigiendose al modulo Docente", Alert.AlertType.INFORMATION);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedDocente.fxml"));
                    Parent root = loader.load();

                    LoggedDocenteController controller = loader.getController();
                    controller.setController(authController);
                    controller.setFormatoAController(new FormatoAController(new ServicioFormatoA(new FormatoARepositorio())));

                    Stage stage = (Stage) btnIngresar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } else {
                AlertUtil.mostrarAlerta("Ingreso Incorrecto", "Correo o contraseña incorrectos", Alert.AlertType.WARNING);
                txtCorreo.setText("");
                txtContraseña.setText("");
            }
        }

    }

    @FXML
    private void handleClickPane(MouseEvent event) {
        // Quita el foco del TextField (y de cualquier otro nodo que lo tenga)
        pnDatos.requestFocus();
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

    private boolean espaciosVacios() {

        if (txtCorreo.getText().trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacio vacio", "Ingrese un correo", Alert.AlertType.WARNING);
            return true;
        }
        if (txtContraseña.getText().trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacio vacio", "Ingrese la contraseña", Alert.AlertType.WARNING);
            return true;
        }
        return false;
    }

    @Override
    public void update(Object o) {
        AlertUtil.mostrarAlerta("Notificacion", "Usuario registrado", Alert.AlertType.INFORMATION);
    }
}
