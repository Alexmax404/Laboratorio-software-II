package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.events.DomainEvent;
import co.unicauca.gestiontg.factory.FormatoAControllerFactory;
import co.unicauca.gestiontg.infra.Observer;
import co.unicauca.gestiontg.service.ServicioFormatoA;
import co.unicauca.gestiontg.showcase.utilities.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
    private SceneRouter router;
    private FormatoAControllerFactory formatoFactory;

    public void setController(AuthController authController) {
        this.authController = authController;
        initializeObserver();
    }

    public void setRouter(SceneRouter router) {
        this.router = router;
    }

    public void setFormatoFactory(FormatoAControllerFactory factory) {
        this.formatoFactory = factory;
    }

    public void initializeObserver() {
        if (authController != null) {
            authController.getEventPublisher().addObserver(this);
        }
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

        if (authController.validarEspaciosVacios(correo, contrasenia) == false) {
            if (validar) {
                Optional<String> rol = authController.getRolUsuario(correo);
                if (rol.get().equals("Estudiante")) {
                    router.goToStudentModule(authController);
                } else if(rol.get().equals("Docente")) {
                    FormatoAController formatoCtrl = (formatoFactory != null)
                            ? formatoFactory.create()
                            : new FormatoAController(new ServicioFormatoA(new FormatoARepositorio()));
                    router.goToTeacherModule(authController, formatoCtrl);
                } else if(rol.get().equals("Coordinador")){
                    FormatoAController formatoCtrl = (formatoFactory != null)
                            ? formatoFactory.create()
                            : new FormatoAController(new ServicioFormatoA(new FormatoARepositorio()));
                    router.goToCoordinadorModule(authController, btnIngresar);
                }
            }
        }
    }

    @FXML
    void switchToRegister(ActionEvent event) {
        try {
            if (authController == null || router == null) {
                throw new IllegalStateException("Dependencias no inyectadas");
            }
            router.goToRegister(authController);
        } catch (IOException ex) {
            showError(ex);
        }
    }

    @FXML
    private void handleClickPane(MouseEvent event) {
        pnDatos.requestFocus();
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        AlertUtil.mostrarAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
    }

    @Override
    public void update(Object o) {
        AlertUtil.mostrarAlerta("Notificacion", "Usuario registrado", Alert.AlertType.INFORMATION);
    }

    @Override
    public void update(DomainEvent event) {
        switch (event.getName()) {
            case USER_REGISTERED:
                Usuario u = (Usuario) event.getData();
                AlertUtil.mostrarAlerta("Usuario Registrado", "Bienvenido " + u.getNombreCompleto(), Alert.AlertType.INFORMATION);
                break;
            case LOGIN_FALLIDO:
                AlertUtil.mostrarAlerta("Error", "Credenciales incorrectas", Alert.AlertType.WARNING);
                break;
            case LOGIN_EXITOSO:
                AlertUtil.mostrarAlerta("Bienvenido", "Ingreso exitoso", Alert.AlertType.INFORMATION);
                break;
            case ESPACIOS_VACIOS:
                AlertUtil.mostrarAlerta("Espacios Vacíos", "Correo o contraseña no pueden estar vacío", Alert.AlertType.WARNING);
                break;
            default:
                throw new AssertionError();
        }
    }
}