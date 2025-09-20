package co.unicauca.gestiontg;

import co.unicauca.gestiontg.controller.AuthController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfesorFormatoAController {

    @FXML
    private Button btnExit;
    @FXML
    private ComboBox<String> cbxModalidad;

    @FXML
    private ImageView fadingImage;

    @FXML
    private Pane pnDatos1;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtNombres1;

    @FXML
    private TextField txtNombres11;

    @FXML
    private TextField txtNombres111;

    @FXML
    private TextField txtNombres1111;

    @FXML
    private TextField txtNombres112;

    @FXML
    private TextField txtNombres12;

    @FXML
    private TextField txtNombres13;

    @FXML
    void handleClickPane(MouseEvent event) {
         pnDatos1.requestFocus();
    }
    
    @FXML
    void EventSalir(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedDocente.fxml"));
        Parent root = loader.load();
        

        LoggedDocenteController docenteController = loader.getController();
        docenteController.setController(authController);

        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private AuthController authController;

    public void setController(AuthController authController) {
        this.authController = authController;
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
