package co.unicauca.gestiontg.showcase.controller;

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
            Alert alertaVacio = new Alert(Alert.AlertType.WARNING);
            alertaVacio.setTitle("Advertencia");
            alertaVacio.setHeaderText("Campo vacío");
            alertaVacio.setContentText("Debe escribir al menos un objetivo antes de guardar.");
            alertaVacio.showAndWait();
            return;
        }

        // Guardar el texto
        objetivosGuardados = texto;

        // Alerta de confirmación
        Alert alertaGuardado = new Alert(Alert.AlertType.INFORMATION);
        alertaGuardado.setTitle("Éxito");
        alertaGuardado.setHeaderText("Cambios guardados");
        alertaGuardado.setContentText("Los objetivos han sido guardados correctamente.");
        alertaGuardado.showAndWait();

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
