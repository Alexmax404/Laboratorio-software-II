package co.unicauca.gestiontg;

import co.unicauca.gestiontg.controller.AuthController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.Modality;

public class ProfesorFormatoAController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnFormatoA;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnObjetivoEspecifico;
    
    @FXML
    private Button btnObjetivoGeneral;

    @FXML
    private ComboBox<String> cbxModalidad;

    @FXML
    private ImageView fadingImage;

    @FXML
    private Label lblCoodirectorProyecto;

    @FXML
    private Label lblDirectorProyecto;

    @FXML
    private Label lblEstudiante1;

    @FXML
    private Label lblEstudiante2;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblModalidad;

    @FXML
    private Label lblObjetivos;

    @FXML
    private Label lblTituloPrincipal;

    @FXML
    private Label lblTituloProyecto;

    @FXML
    private Pane pnDatos1;

    @FXML
    private TextField txtCodigoEst1;

    @FXML
    private TextField txtCodigoEst2;

    @FXML
    private TextField txtCoodirectorProyecto;

    @FXML
    private TextField txtDirectorProyecto;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtNombreEst1;

    @FXML
    private TextField txtNombreEst2;

    @FXML
    private TextField txtTituloProyecto;

    @FXML
    void handleClickPane(MouseEvent event) {
         pnDatos1.requestFocus();
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
                // 🔄 Resetear todos los datos
                ObjetivoGeneralController.resetObjetivoGuardado();
                ObjetivosEspecificosController.resetObjetivosGuardados();
                CargarPDFController.resetPDF();

                try {
                    // 🔄 Volver a la ventana principal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedDocente.fxml"));
                    Parent root = loader.load();

                    LoggedDocenteController docenteController = loader.getController();
                    docenteController.setController(authController);

                    Stage stage = (Stage) btnExit.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    private AuthController authController;

    public void setController(AuthController authController) {
        this.authController = authController;
    }
    
    @FXML
    void abrirObjetivoEspecifico(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ObjetivosEspecificos.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ObjetivoGeneral.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Objetivo General");
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
    void abrirSubirPDF(ActionEvent event) {
                try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CargarPDF.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("CargarPDF");
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
    private void initialize() {
        cbxModalidad.getItems().addAll(
                "Trabajo de investigacion",
                "Practica profesional"
        );
        cbxModalidad.setPromptText("Seleccione una opción");


    }

}
