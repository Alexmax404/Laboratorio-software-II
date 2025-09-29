package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.domain.FormatoA;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CorregirFormatoController {

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

    private final FormatoARepositorio repositorio = new FormatoARepositorio();

    @FXML
    void handleClickPane(MouseEvent event) {
        // acción opcional
    }
    
    public void setFormato(FormatoA formato) {
        // Mostrar datos en los labels
        mostrarDatos(formato);
        cbxEstado.getItems().setAll("Aprobado", "Rechazado", "En_Revision");
        cbxEstado.setValue(formato.getEstado().name());
        aplicarColorEstado(cbxEstado.getValue());
        cbxEstado.valueProperty().addListener((obs, oldVal, newVal) -> {
            aplicarColorEstado(newVal);
        });
        txtCorrecciones.setText("");
    }

    @FXML
    void Corregir(ActionEvent event) {

    }

    private void mostrarDatos(FormatoA formato) {
        lblID.setText(formato.getId().toString());
        lblTitulo.setText(formato.getTitulo());
        lblNombre.setText(formato.getDirector());

        try {
            Optional<List<String>> optNombres = repositorio.obtenerNombresEstudiantesPorFormatoId(formato.getId());
            String integrantes = (optNombres.isPresent() && !optNombres.get().isEmpty())
                    ? String.join(" y ", optNombres.get())
                    : "N/A";
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
            case "En_Revision":
                cbxEstado.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-background-radius: 20; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
            default:
                cbxEstado.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: black; -fx-background-radius: 20; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
        }
    }
}
