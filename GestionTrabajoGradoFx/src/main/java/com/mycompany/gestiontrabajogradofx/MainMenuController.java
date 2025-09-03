package com.mycompany.gestiontrabajogradofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    void switchToLogged(ActionEvent event) throws IOException {
        App.setRoot("loggedDocente");
        //App.setRoot("loggedEstudiante");
    }
    
    
    
    @FXML
    private void handleClickPane(MouseEvent event) {
        // Quita el foco del TextField (y de cualquier otro nodo que lo tenga)
        pnDatos.requestFocus();
    }
    


    @FXML
    void switchToRegister(ActionEvent event) throws IOException {
        App.setRoot("RegisterFrame");
    }
    
    
}
