package co.unicauca.gestiontg;

import co.unicauca.gestiontg.access.GestionUsuario;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.infra.Observer;
import co.unicauca.gestiontg.service.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.util.Duration;

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
    
    private Servicio servicio;
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    @FXML
    private void initialize() {
        
    }
    @FXML
    void switchToLogged(ActionEvent event) throws IOException {
        String correo = txtCorreo.getText();
        String contrasenia = txtContraseña.getText();
        String rol = servicio.obtenerRolUsuario(correo);
        
        boolean validar = servicio.inicioSesion(correo, contrasenia);
        
         if (espaciosVacios() == false) {
            if (validar) {
                if (rol.equals("Estudiante")) {
                    mostrarAlerta("Bienvenido!", "Dirigiendose al modulo Estudiante", Alert.AlertType.INFORMATION);
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedEstudiante.fxml"));
                    Parent root = loader.load();

                    LoggedEstudianteController estudiante = loader.getController();
                    estudiante.setServicio(servicio);

                    Stage stage = (Stage) btnIngresar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    mostrarAlerta("Bienvenido!", "Dirigiendose al modulo Docente", Alert.AlertType.INFORMATION);
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedDocente.fxml"));
                    Parent root = loader.load();

                    LoggedDocenteController docente = loader.getController();
                    docente.setServicio(servicio);

                    Stage stage = (Stage) btnIngresar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } else {
                mostrarAlerta("Ingreso Incorrecto", "Correo o contraseña incorrectos", Alert.AlertType.WARNING);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterFrame.fxml"));
        Parent root = loader.load();

        RegisterFrameController registerController = loader.getController();
        registerController.setServicio(servicio);

        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    
    private boolean espaciosVacios() {
        
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarAlerta("Espacio vacio", "Ingrese un correo", Alert.AlertType.WARNING);
            return true;
        }
        if (txtContraseña.getText().trim().isEmpty()) {
            mostrarAlerta("Espacio vacio", "Ingrese la contraseña", Alert.AlertType.WARNING);
            return true;
        }
        return false;
    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);

        // Cambiar título e ícono de ventana
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);

        // Crear un Label personalizado para el mensaje
            Label etiqueta = new Label(mensaje);
        etiqueta.setWrapText(true);
        etiqueta.setStyle("-fx-font-Tebuchet MS: 14px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #2c3e50;");

        // Meter el Label en un contenedor para darle padding
            VBox contenedor = new VBox(etiqueta);
        contenedor.setSpacing(10);
        contenedor.setPadding(new Insets(10));

        alerta.getDialogPane().setContent(contenedor);

        // Aplicar estilo al cuadro de diálogo completo
        alerta.getDialogPane().setStyle(
            "-fx-background-color: #f9f9f9; " +
            "-fx-border-color: #ABBEF6; " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 5px; " +
            "-fx-background-radius: 5px;"
        );

        // Cambiar estilo de los botones
        alerta.getDialogPane().lookupButton(ButtonType.OK)
              .setStyle("-fx-background-color: #1E2C9E; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 7px;");

        alerta.showAndWait();
    }
    

    
    @Override
    public void update(Object o) {
        mostrarAlerta("Notificacion", "Usuario registrado", Alert.AlertType.INFORMATION);
    }
}
