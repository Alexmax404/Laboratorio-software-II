package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.showcase.utilities.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ObjetivosEspecificosController {

    public static void resetObjetivosGuardados() {
        objetivosGuardados = "";
    }

    @FXML
    private Button btnGuardar;

    @FXML
    private TextArea txtObjetivos;

    // Variable estática para guardar el texto entre aperturas
    private static String objetivosGuardados = "";

    @FXML
    public void initialize() {
        // Cargar el texto previo si existe
        txtObjetivos.setText(objetivosGuardados);
    }
    
    @FXML
    void btnGuardarClick(ActionEvent event) {
        String texto = txtObjetivos.getText().trim();

        if (texto.isEmpty()) {
            //️ Mostrar alerta si está vacío
            AlertUtil.mostrarAlerta("Campo vacío", "Debe escribir al menos un objetivo antes de guardar.", Alert.AlertType.WARNING);
        }

        // Guardar el texto
        objetivosGuardados = texto;

        // Alerta de confirmación
        AlertUtil.mostrarAlerta("Cambios guardados", "Los objetivos han sido guardados correctamente.", Alert.AlertType.INFORMATION);

        // Cerrar la ventana
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    // Método opcional para acceder desde otras clases si lo necesitas
    public static String getObjetivosGuardados() {
        return objetivosGuardados;
    }
        @FXML
    void handleClickPane(MouseEvent event) {

    }
}
