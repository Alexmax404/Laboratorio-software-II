/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.gestiontg;

import co.unicauca.gestiontg.access.GestionUsuario;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.domain.EnumPrograma;
import co.unicauca.gestiontg.domain.EnumRol;
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.service.Servicio;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    
    private Servicio servicio = null;
    private Usuario user = null;
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
        
    @FXML
    private void EventRegistrar (ActionEvent event) throws IOException {
        if (espaciosVacios() == false){
            datos();
            if(validarCorreo() && validarContrasenia())
                registrarUsuario();
            else if(!validarCorreo()){
                mostrarAlerta(null, servicio.validarCorreoInstitucional(user.getCorreo()), Alert.AlertType.WARNING);
            }
            else if (!validarContrasenia()){
                mostrarAlerta(null, servicio.validarContrasenia(user.getContrasenia()), Alert.AlertType.WARNING);
            }
                
        }
    }
    @FXML
    private void EventSalir (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainController = loader.getController();
        mainController.setServicio(servicio); 

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
    
    private boolean espaciosVacios(){

        if (txtNombres.getText().trim().isEmpty()){
            mostrarAlerta("Espacio vacio.", "Ingrese su/s nombre/s", Alert.AlertType.WARNING);
            return true;
        }
        if (txtApellidos.getText().trim().isEmpty()){
            mostrarAlerta("Espacio vacio.", "Ingrese sus apellidos", Alert.AlertType.WARNING);
            return true;
        }
        if (txtCorreo.getText().trim().isEmpty()){
            mostrarAlerta("Espacio vacio", "Ingrese un correo", Alert.AlertType.WARNING);
            return true;
        }
        if (txtContrasenia.getText().trim().isEmpty()){
            mostrarAlerta("Espacio vacio", "Ingrese la contraseña", Alert.AlertType.WARNING);
            return true;
        }
        if (cbxPrograma.getValue() == null){
            mostrarAlerta("Espacio vacio", "Seleccione el programa al cual pertenece", Alert.AlertType.WARNING);
            return true;
        }
        if (chkbDocente.isSelected() == false && chkbEstudiante.isSelected()==false){
            mostrarAlerta("Espacio vacio", "Seleccione un rol", Alert.AlertType.WARNING);
            return true;
        }
        return false;
    }
    
     private void datos(){
        
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String correo= txtCorreo.getText();
        String contrasenia = txtContrasenia.getText();
        String celular =  txtCelular.getText();
        
        EnumPrograma programa = null;
        switch(cbxPrograma.getValue()){
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
        if(chkbDocente.isSelected()){
            rol = EnumRol.Docente;
        }else{
            rol = EnumRol.Estudiante;
        }
        
        user = new Usuario(nombres, apellidos, celular, programa, rol, correo, contrasenia);
    }
     
    private boolean validarCorreo(){
        return servicio.validarCorreoInstitucional(user.getCorreo()).equals("OK");
    }
    
    private boolean validarContrasenia(){
        return servicio.validarContrasenia(user.getContrasenia()).equals("OK");
    }

    private void registrarUsuario() throws IOException {
        try {
            if (servicio.registrarUsuario(user)) {
                mostrarAlerta(null, "Cuenta registrada con exito.", Alert.AlertType.INFORMATION);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
                Parent root = loader.load();

                MainMenuController mainController = loader.getController();
                mainController.setServicio(servicio); 

                Stage stage = (Stage) btnRegistrar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                
                servicio.notifyAllObserves();
            } else {
                mostrarAlerta(null, "Correo ya en uso.", Alert.AlertType.WARNING);
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error al crear cuenta.", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML

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

}

