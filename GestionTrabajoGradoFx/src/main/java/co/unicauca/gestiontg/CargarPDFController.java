package co.unicauca.gestiontg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class CargarPDFController {

    @FXML
    private Button btnGuardar;

    @FXML
    private Label lblTitulo;

    @FXML
    private Pane pnCargarPDF;

    @FXML
    private Pane pnDatos1;

    @FXML
    private ImageView imagenPDF;

    @FXML
    private Label lblNombrePDF;

    // 🔹 Guardamos el archivo seleccionado (pero aún no copiado)
    private File fileSeleccionado;

    // 🔹 Guardamos el archivo actual copiado en la carpeta
    private static File archivoCargado;

    @FXML
    public void initialize() {
        // Ocultar elementos si no hay archivo copiado
        if (archivoCargado == null) {
            imagenPDF.setVisible(false);
            lblNombrePDF.setVisible(false);
        } else {
            imagenPDF.setVisible(true);
            lblNombrePDF.setVisible(true);
            lblNombrePDF.setText(archivoCargado.getName());
        }

        // Configurar eventos de Drag & Drop
        pnCargarPDF.setOnDragOver(event -> {
            if (event.getGestureSource() != pnCargarPDF && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        pnCargarPDF.setOnDragDropped(this::handleFileDropped);
        pnCargarPDF.setOnMouseClicked(this::handleClickPanelPDF);
    }

    @FXML
    void btnGuardarClick(ActionEvent event) {
        if (fileSeleccionado == null) {
            mostrarAlerta("Advertencia", "No se ha seleccionado ningún PDF.", Alert.AlertType.WARNING);
            return;
        }

        copiarArchivo(fileSeleccionado); // Aquí sí lo copiamos realmente
        mostrarAlerta("Guardar", "Cambios guardados con éxito.", Alert.AlertType.INFORMATION);

        // Cerrar ventana, pero mantener referencia
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    private void handleFileDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            if (file.getName().toLowerCase().endsWith(".pdf")) {
                fileSeleccionado = file;
                actualizarUIConArchivo(fileSeleccionado);
                success = true;
            } else {
                mostrarAlerta("Error", "El archivo no es un PDF válido.", Alert.AlertType.ERROR);
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void handleClickPanelPDF(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

        File file = fileChooser.showOpenDialog((Stage) pnCargarPDF.getScene().getWindow());
        if (file != null) {
            fileSeleccionado = file;
            actualizarUIConArchivo(fileSeleccionado); // ✅ ahora sí actualizamos con el archivo seleccionado
        }
    }

    private void copiarArchivo(File file) {
        try {
            Path carpetaDestino = Path.of("src", "main", "resources", "pdfs");
            if (!Files.exists(carpetaDestino)) {
                Files.createDirectories(carpetaDestino);
            }

            // Eliminar el archivo anterior si existía
            if (archivoCargado != null && archivoCargado.exists()) {
                Files.deleteIfExists(archivoCargado.toPath());
            }

            Path destino = carpetaDestino.resolve(file.getName());
            Files.copy(file.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

            archivoCargado = destino.toFile(); // Guardamos referencia
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo copiar el archivo:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void actualizarUIConArchivo(File file) {
        imagenPDF.setVisible(true);
        lblNombrePDF.setVisible(true);
        lblNombrePDF.setText(file.getName());
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    void handleClickPane(MouseEvent event) {
        pnDatos1.requestFocus();
    }

    public static void resetPDF() {
        if (archivoCargado != null && archivoCargado.exists()) {
            try {
                Files.deleteIfExists(archivoCargado.toPath());
            } catch (IOException e) {
                System.err.println("No se pudo eliminar el archivo: " + e.getMessage());
            }
        }
        archivoCargado = null;
    }
}
