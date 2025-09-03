/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiontrabajogradofx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

    @FXML
    void switchToMainMenu(ActionEvent event) throws IOException {
        App.setRoot("mainMenu");
    }
    @FXML
    private void handleClickPane(MouseEvent event) {
        // Quita el foco del TextField (y de cualquier otro nodo que lo tenga)
        pnDatos.requestFocus();
    }
    @FXML
    private void initialize() {
        cbxPrograma.getItems().addAll(
                "Ingeniería de Sistemas",
                "Derecho",
                "Medicina",
                "Arquitectura"
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

}

