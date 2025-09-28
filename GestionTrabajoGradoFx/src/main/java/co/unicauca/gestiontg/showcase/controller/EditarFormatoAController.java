package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.FormatoA;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditarFormatoAController implements Initializable {

    @FXML
    private Button btnExit, btnFormatoA, btnGuardar, btnObjetivoEspecifico, btnObjetivoGeneral;

    @FXML
    private ComboBox<String> cbxModalidad;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView fadingImage;

    @FXML
    private Label lblCoodirectorProyecto, lblDirectorProyecto, lblEstudiante1,
                  lblEstudiante2, lblFecha, lblModalidad, lblObjetivos,
                  lblObjetivos1, lblTituloPrincipal, lblTituloProyecto;

    @FXML
    private Pane pnDatos1;

    @FXML
    private TextField txtCoodirectorProyecto, txtCorreoEst1, txtCorreoEst2,
                      txtDirectorProyecto, txtNombreEst1, txtNombreEst2, txtTituloProyecto;

    private AuthController authController;
    private FormatoAController formatoAController;


    public void setAuthController(AuthController authController) {
        this.authController = authController;
    }

    public void setFormatoAController(FormatoAController formatoAController) {
        this.formatoAController = formatoAController;
    }
    private FormatoA formatoSeleccionado;

    public void setFormatoA(FormatoA formato) {
        this.formatoSeleccionado = formato;

        if (formato != null) {
            txtTituloProyecto.setText(formato.getTitulo());
            // Título

            // Director y Codirector
            txtDirectorProyecto.setText(formato.getDirector());
            txtCoodirectorProyecto.setText(formato.getCoDirector());

            // Estudiantes (por ahora mostramos los UUID)
            if (formato.getEstudianteId1() != null) {
                txtNombreEst1.setText(formato.getEstudianteId1().toString());
            }
            if (formato.getEstudianteId2() != null) {
                txtNombreEst2.setText(formato.getEstudianteId2().toString());
            }

            // Modalidad
            if (formato.getModalidad() != null) {
                cbxModalidad.getSelectionModel().select(formato.getModalidad().toString());
            }

            // Fecha
            if (formato.getFechaPresentacion() != null) {
                datePicker.setValue(formato.getFechaPresentacion());
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         
    }

@FXML
    void EventSalir(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar salida");
        confirmacion.setHeaderText("Salir sin guardar");
        confirmacion.setContentText("¿Está seguro de que desea salir sin guardar los cambios?");

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        confirmacion.getButtonTypes().setAll(btnSi, btnNo);

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == btnSi) {
                // 🔄 Resetear datos temporales
                ObjetivoGeneralController.resetObjetivoGuardado();
                ObjetivosEspecificosController.resetObjetivosGuardados();
                CargarPDFController.resetPDF();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedDocente.fxml"));
                    Parent root = loader.load();

                    LoggedDocenteController docenteController = loader.getController();
                    docenteController.setController(authController);
                    docenteController.setFormatoAController(formatoAController); // 👈 pasar también FormatoAController

                    Stage stage = (Stage) btnExit.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void abrirObjetivoEspecifico(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/ObjetivosEspecificos.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Objetivos Específicos");
            stage.setScene(new Scene(root));

            // 🔑 Hace que la ventana sea modal → bloquea la principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnObjetivoEspecifico.getScene().getWindow());

            // No permitir que se redimensione
            stage.setResizable(false);

            // Bloquea la ejecución hasta que la ventana se cierre
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirObjetivoGeneral(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/ObjetivoGeneral.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Objetivo General");
            stage.setScene(new Scene(root));

            // Hace que la ventana sea modal → bloquea la principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnObjetivoEspecifico.getScene().getWindow());

            // No permitir que se redimensione
            stage.setResizable(false);

            // Bloquea la ejecución hasta que la ventana se cierre
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
     void abrirSubirPDF(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/CargarPDF.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("CargarPDF");
                stage.setScene(new Scene(root));

                // Hace que la ventana sea modal → bloquea la principal
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(btnObjetivoEspecifico.getScene().getWindow());

                // No permitir que se redimensione
                stage.setResizable(false);

                // Bloquea la ejecución hasta que la ventana se cierre
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    @FXML
    void guardarFormatoA(ActionEvent event) {
    }

    @FXML
    void handleClickPane(MouseEvent event) {
         pnDatos1.requestFocus();
    }
}
