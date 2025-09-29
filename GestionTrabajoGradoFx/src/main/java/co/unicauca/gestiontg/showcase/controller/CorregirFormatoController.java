package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.EvaluacionCoordinadorController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.EnumDecision;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.factory.FormatoAControllerFactory;
import co.unicauca.gestiontg.showcase.utilities.AlertUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CorregirFormatoController {

    @FXML
    private Button btnExit;
    @FXML
    private Button btnCorregir;

    @FXML
    private ComboBox<String> cbxEstado;

    @FXML
    private Label lblID;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblNombres;

    @FXML
    private Label lblTitulo;

    @FXML
    private Pane pnDatos1;

    @FXML
    private TextArea txtCorrecciones;
    private FormatoAController formatoAController;
    private AuthController authController;
    private EvaluacionCoordinadorController evalController;

    public void setFormatoAController(FormatoAController formatoAController) {
        this.formatoAController = formatoAController;
    }

    public void setController(AuthController authController) {
        this.authController = authController;
    }

    public void setEvalController(EvaluacionCoordinadorController evalController) {
        this.evalController = evalController;
    }

    public void initialize() {
        formatoAController = new FormatoAControllerFactory().create();
    }

    @FXML
    void EventSalir(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedCoordinador.fxml"));
            Parent root = loader.load();

            LoggedCoordinadorController coordinadorController = loader.getController();
            coordinadorController.setController(authController);
            coordinadorController.setFormatoAController(formatoAController);

            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleClickPane(MouseEvent event) {
        pnDatos1.requestFocus();
    }

    public void setFormato(FormatoA formato) {
        // Mostrar datos en los labels
        mostrarDatos(formato);
        cbxEstado.getItems().setAll("Aprobado", "Rechazado", "Correcciones");
        if (formato.getEstado().name().equals("En_Revision")) {
            cbxEstado.setValue("Correcciones");
        }

        aplicarColorEstado(cbxEstado.getValue());
        cbxEstado.valueProperty().addListener((obs, oldVal, newVal) -> {
            aplicarColorEstado(newVal);
        });
        txtCorrecciones.setText("");
    }

    @FXML
    void Guardar(ActionEvent event) {
        String estadoSeleccionado = cbxEstado.getValue();
        if (estadoSeleccionado == null || estadoSeleccionado.isEmpty()) {
            AlertUtil.mostrarAlerta("Error", "Debe seleccionar un estado para la evaluación.", Alert.AlertType.WARNING);
            return;
        }

        String comentarios = txtCorrecciones.getText().trim();
        if ((estadoSeleccionado.equals("Correcciones") || estadoSeleccionado.equals("Rechazado"))
                && comentarios.isEmpty()) {
            AlertUtil.mostrarAlerta("Error", "Debe ingresar comentarios para esta decisión.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Obtener IDs necesarios
            UUID formatoId = UUID.fromString(lblID.getText());
            String formatoIdStr = lblID.getText();
            String formatoVersionId = formatoAController.obtenerFormatoVersionPorIDFormato(formatoId).toString();
            String coordinadorId = authController.getUsuarioLogueado().getId().toString();
            // Guardar evaluación usando el servicio del controller
            String evalId = evalController.evaluar(formatoIdStr, formatoVersionId, coordinadorId, estadoSeleccionado, comentarios);
            AlertUtil.mostrarAlerta("Éxito", "Evaluación guardada correctamente. ID: " + evalId, Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.mostrarAlerta("Error", "No se pudo guardar la evaluación: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarDatos(FormatoA formato) {
        lblID.setText(formato.getId().toString());
        lblTitulo.setText(formato.getTitulo());
        lblNombre.setText(formato.getDirector());

        System.out.println("FormatoID: " + formato.getId());
        System.out.println("formatoAController == null? " + (formatoAController == null));

        cargarNombresEstudiantes(formato.getId());
    }

    private void cargarNombresEstudiantes(UUID formatoId) {
        try {
            Optional<List<String>> optNombres = formatoAController.obtenerNombresEstudiantesPorFormatoId(formatoId);
            String integrantes = optNombres
                    .filter(list -> !list.isEmpty())
                    .map(list -> String.join(" y ", list))
                    .orElse("N/A");
            lblNombres.setText(integrantes);
        } catch (SQLException e) {
            e.printStackTrace();
            lblNombres.setText("Error al cargar");
        }
    }

    /**
     * Cambia el color del ComboBox dependiendo del estado.
     */
    private void aplicarColorEstado(String estado) {
        if (estado == null) {
            return;
        }

        switch (estado) {
            case "Aprobado":
                cbxEstado.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
            case "Rechazado":
                cbxEstado.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
            case "Correcciones":
                cbxEstado.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-background-radius: 20; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
            default:
                cbxEstado.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: black; -fx-background-radius: 20; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
        }
    }
}
