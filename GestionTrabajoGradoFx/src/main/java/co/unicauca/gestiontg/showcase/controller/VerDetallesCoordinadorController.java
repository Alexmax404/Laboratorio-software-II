package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.domain.FormatoAVersion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class VerDetallesCoordinadorController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnVerPDF;

    @FXML
    private Label lblCodirector;

    @FXML
    private Label lblDirector;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblId;

    @FXML
    private Label lblModalidad;

    @FXML
    private Label lblNombreIntegrantes;

    @FXML
    private Label lblObjetivosEspecificos;

    @FXML
    private Label lblObjetivosGenerales;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblVersion;

    private FormatoA formato; // 👈 Formato seleccionado en la tabla
    private FormatoAVersion ultimaVersion; // 👈 Última versión obtenida de BD

    private final FormatoARepositorio repositorio = new FormatoARepositorio();

    /**
     * Recibe el FormatoA seleccionado y busca su última versión
     */
    public void setFormato(FormatoA formato) {
        this.formato = formato;

        try {
            Optional<FormatoAVersion> opt = repositorio.obtenerDetalleFormato(formato.getId());
            if (opt.isPresent()) {
                ultimaVersion = opt.get();
                mostrarDatos();
            } else {
                lblTitulo.setText("No se encontró versión del formato");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblTitulo.setText("Error al cargar datos");
        }
    }

    /**
     * Muestra en pantalla los datos de la última versión
     */
    private void mostrarDatos() {
        if (ultimaVersion == null) return;

        lblId.setText(ultimaVersion.getId().toString());
        lblTitulo.setText(ultimaVersion.getTitulo());
        lblDirector.setText(ultimaVersion.getDirector());
        lblCodirector.setText(ultimaVersion.getCoDirector() != null ? ultimaVersion.getCoDirector() : "N/A");
        lblModalidad.setText(ultimaVersion.getModalidad() != null ? ultimaVersion.getModalidad().name() : "N/A");
        lblObjetivosGenerales.setText(ultimaVersion.getObjetivosGenerales() != null ? ultimaVersion.getObjetivosGenerales() : "N/A");
        lblObjetivosEspecificos.setText(ultimaVersion.getObjetivosEspecificos() != null ? ultimaVersion.getObjetivosEspecificos() : "N/A");
        lblVersion.setText(String.valueOf(ultimaVersion.getVersion()));
        lblFecha.setText(ultimaVersion.getFechaPresentacion() != null ? ultimaVersion.getFechaPresentacion().toString() : "Sin fecha");
        lblNombreIntegrantes.setText("No disponible");
    }

    /**
     * Cierra la ventana
     */
    @FXML
    void EventSalir(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    /**
     * Abre o descarga el PDF
     */
    @FXML
    void abrirSubirPDF(ActionEvent event) {
        if (ultimaVersion != null) {
            System.out.println("Abrir PDF: " + ultimaVersion.getArchivoFormatoPath());
            // Aquí puedes usar Desktop.getDesktop().open(new File(path)) o similar
        }
    }

    @FXML
    void handleClickPane(MouseEvent event) {
        // Aquí puedes manejar clicks en el panel principal si es necesario
    }
}
