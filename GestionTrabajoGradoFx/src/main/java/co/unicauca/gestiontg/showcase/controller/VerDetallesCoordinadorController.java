package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.domain.FormatoAVersion;
import java.awt.Desktop;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VerDetallesCoordinadorController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnVerPDF;

    @FXML
  // 🔹 Único label para mostrar todo

    private FormatoA formato; 
    private FormatoAVersion ultimaVersion; 
    private final FormatoARepositorio repositorio = new FormatoARepositorio();
    
    @FXML
    private Pane pnDatos1;
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
                lblGeneral.getChildren().clear();
                lblGeneral.getChildren().add(new Text("No se encontró versión del formato"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblGeneral.getChildren().clear();
            lblGeneral.getChildren().add(new Text("Error al cargar datos"));
        }
    }

    /**
     * Muestra toda la información en un solo Label organizado
     */
   @FXML
private TextFlow lblGeneral;

private void mostrarDatos() {
    if (ultimaVersion == null) return;

    lblGeneral.getChildren().clear(); // limpiar antes de mostrar

    // Helper para títulos en negrilla
    java.util.function.Function<String, Text> bold = txt -> {
        Text t = new Text(txt);
        t.setStyle("-fx-font-weight: bold;");
        return t;
    };

    // Helper para contenido normal (con wrap)
    java.util.function.Function<String, Text> normal = txt -> new Text(txt + "\n");

    lblGeneral.getChildren().addAll(
        bold.apply("📄 Formato ID: "), normal.apply(ultimaVersion.getId().toString()),
        bold.apply("Título: "), normal.apply(ultimaVersion.getTitulo()),
        bold.apply("Director: "), normal.apply(ultimaVersion.getDirector()),
        bold.apply("Codirector: "), normal.apply(ultimaVersion.getCoDirector() != null ? ultimaVersion.getCoDirector() : "N/A"),
        bold.apply("Modalidad: "), normal.apply(ultimaVersion.getModalidad() != null ? ultimaVersion.getModalidad().name() : "N/A"),
        bold.apply("Objetivos Generales:\n"), normal.apply(ultimaVersion.getObjetivosGenerales() != null ? wrapText(ultimaVersion.getObjetivosGenerales(), 200) : "N/A"),
        bold.apply("Objetivos Específicos:\n"), normal.apply(ultimaVersion.getObjetivosEspecificos() != null ? wrapText(ultimaVersion.getObjetivosEspecificos(), 200) : "N/A"),
        bold.apply("Versión: "), normal.apply(String.valueOf(ultimaVersion.getVersion())),
        bold.apply("Fecha Presentación: "), normal.apply(ultimaVersion.getFechaPresentacion() != null ? ultimaVersion.getFechaPresentacion().toString() : "Sin fecha")
    );

    // 🔹 Nombres de estudiantes
    try {
        Optional<List<String>> optNombres = repositorio.obtenerNombresEstudiantesPorFormatoId(formato.getId());
        String integrantes = (optNombres.isPresent() && !optNombres.get().isEmpty())
                ? String.join(" y ", optNombres.get())
                : "N/A";
        lblGeneral.getChildren().addAll(
            bold.apply("Integrantes: "), normal.apply(integrantes)
        );
    } catch (SQLException e) {
        e.printStackTrace();
        lblGeneral.getChildren().addAll(
            bold.apply("Integrantes: "), normal.apply("Error al cargar")
        );
    }
}

/**
 * Envuelve texto largo insertando saltos de línea cada "lineLength" caracteres.
 */
private String wrapText(String text, int lineLength) {
    StringBuilder wrapped = new StringBuilder();
    int count = 0;

    for (String word : text.split(" ")) {
        if (count + word.length() > lineLength) {
            wrapped.append("\n");
            count = 0;
        }
        wrapped.append(word).append(" ");
        count += word.length() + 1;
    }
    return wrapped.toString().trim();
}



    @FXML
    void EventSalir(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

        @FXML
    void abrirSubirPDF(ActionEvent event) {
        if (ultimaVersion != null && ultimaVersion.getArchivoFormatoPath() != null) {
            try {
                // 📂 Tomar ruta absoluta directamente
                File file = new File(ultimaVersion.getArchivoFormatoPath());

                if (file.exists()) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file); // abre con la app predeterminada del SO
                    } else {
                        System.out.println("El sistema no soporta la apertura automática.");
                    }
                } else {
                    System.out.println("El archivo no existe: " + file.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al intentar abrir el archivo.");
            }
        }
    }




    @FXML
    void handleClickPane(MouseEvent event) {
         pnDatos1.requestFocus();
    }
}
