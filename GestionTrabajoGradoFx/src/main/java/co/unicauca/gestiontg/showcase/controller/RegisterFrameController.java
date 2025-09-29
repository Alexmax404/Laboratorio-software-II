package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.domain.EnumPrograma;
import co.unicauca.gestiontg.domain.EnumRol;
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.showcase.controller.MainMenuController;
import co.unicauca.gestiontg.showcase.utilities.AlertUtil;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author glenn
 */
public class RegisterFrameController {

    @FXML
    private CheckBox chkbDocente;

    @FXML
    private CheckBox chkbEstudiante;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnExit;

    @FXML
    private ComboBox<String> cbxPrograma;

    @FXML
    private Pane pnDatos;

    @FXML
    private Pane pnMainMenu;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtCelular;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombres;

    private Usuario user = null;    
    
    private AuthController authController;
    
    public void setController(AuthController authController){
        this.authController = authController;
    }

    @FXML
    private void EventRegistrar(ActionEvent event) throws IOException {
        if (espaciosVacios() == false) {
            datos();
            if (validarCorreo() && validarContrasenia()) {
                registrarUsuario();
            } else if (!validarCorreo()) {
                AlertUtil.mostrarAlerta(null, authController.validarCorreoInstitucional(user.getCorreo()), Alert.AlertType.WARNING);
            } else if (!validarContrasenia()) {
                AlertUtil.mostrarAlerta(null, authController.validarContrasenia(user.getContrasenia()), Alert.AlertType.WARNING);
            }

        }
    }

    @FXML
    private void EventSalir(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/mainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainController = loader.getController();
        mainController.setController(authController);

        Stage stage = (Stage) btnRegistrar.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleClickPane(MouseEvent event) {
        // Quita el foco del TextField (y de cualquier otro nodo que lo tenga)
        pnDatos.requestFocus();
    }

    @FXML
    private void initialize() {
        cbxPrograma.getItems().addAll(
                "Ingenieria De Sistemas",
                "Ingenieria Electronica Y Telecomunicaciones",
                "Automatica Industrial",
                "Tecnologia Industrial"
        );
        cbxPrograma.setPromptText("Seleccione una opción");
        // Si se marca "Docente", desmarca "Estudiante"
        chkbDocente.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                chkbEstudiante.setSelected(false);
            }
        });

        // Si se marca "Estudiante", desmarca "Docente"
        chkbEstudiante.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                chkbDocente.setSelected(false);
            }
        });

    }

    private boolean espaciosVacios() {

        if (txtNombres.getText().trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacio vacio.", "Ingrese su/s nombre/s", Alert.AlertType.WARNING);
            return true;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacio vacio.", "Ingrese sus apellidos", Alert.AlertType.WARNING);
            return true;
        }
        if (txtCorreo.getText().trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacio vacio", "Ingrese un correo", Alert.AlertType.WARNING);
            return true;
        }
        if (txtContrasenia.getText().trim().isEmpty()) {
            AlertUtil.mostrarAlerta("Espacio vacio", "Ingrese la contraseña", Alert.AlertType.WARNING);
            return true;
        }
        if (cbxPrograma.getValue() == null) {
            AlertUtil.mostrarAlerta("Espacio vacio", "Seleccione el programa al cual pertenece", Alert.AlertType.WARNING);
            return true;
        }
        if (chkbDocente.isSelected() == false && chkbEstudiante.isSelected() == false) {
            AlertUtil.mostrarAlerta("Espacio vacio", "Seleccione un rol", Alert.AlertType.WARNING);
            return true;
        }
        return false;
    }

    private void datos() {

        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String correo = txtCorreo.getText();
        String contrasenia = txtContrasenia.getText();
        String celular = txtCelular.getText();

        EnumPrograma programa = null;
        switch (cbxPrograma.getValue()) {
            case "Ingenieria De Sistemas":
                programa = EnumPrograma.IngenieriaDeSistemas;
                break;
            case "Ingenieria Electronica Y Telecomunicaciones":
                programa = EnumPrograma.IngenieriaElectronicaYTelecomunicaciones;
                break;
            case "Automatica Industrial":
                programa = EnumPrograma.AutomaticaIndustrial;
                break;
            case "Tecnologia Industrial":
                programa = EnumPrograma.TecnologiaIndustrial;
                break;
        }

        EnumRol rol;
        if (chkbDocente.isSelected()) {
            rol = EnumRol.Docente;
        } else {
            rol = EnumRol.Estudiante;
        }

        user = new Usuario(nombres, apellidos, celular, programa, rol, correo, contrasenia);
    }

    private boolean validarCorreo() {
        return authController.validarCorreoInstitucional(user.getCorreo()).equals("OK");
    }

    private boolean validarContrasenia() {
        return authController.validarContrasenia(user.getContrasenia()).equals("OK");
    }

    private void registrarUsuario() throws IOException {
        try {
            if (authController.registerUser(user)){
                AlertUtil.mostrarAlerta(null, "Cuenta registrada con exito.", Alert.AlertType.INFORMATION);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/mainMenu.fxml"));
                Parent root = loader.load();

                MainMenuController mainController = loader.getController();
                mainController.setController(authController);
                Stage stage = (Stage) btnRegistrar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                
            } else {
                AlertUtil.mostrarAlerta(null, "Correo ya en uso.", Alert.AlertType.WARNING);
            }
        } catch (SQLException ex) {
            AlertUtil.mostrarAlerta("Error al crear cuenta.", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
