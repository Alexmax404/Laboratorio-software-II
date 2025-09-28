package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.FormatoA;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ElegirFormatoController {
    @FXML
    private Button btnExit;

    @FXML
    private Pane pnDatos1;

    @FXML
    private TableView<FormatoA> tbFormatos;

    @FXML
    private TableColumn<FormatoA, String> colTitulo;

    private AuthController authController;
    private FormatoAController formatoAController;

    // 👉 Lista observable para refrescar automáticamente la tabla
    private ObservableList<FormatoA> data = FXCollections.observableArrayList();
    

    public void setFormatoAController(FormatoAController formatoAController) {
        this.formatoAController = formatoAController;
    }

    public void setController(AuthController authController) throws SQLException {
        this.authController = authController;

        // Obtener formatos desde el controlador
        List<FormatoA> formatos = formatoAController.listarFormatosByUsuario(
                authController.getUsuarioLogueado().getId().toString()
        );

        // Debug en consola
        System.out.println("Usuario logueado: " + authController.getUsuarioLogueado().getId());
        for (FormatoA f : formatos) {
            System.out.println("Formato docenteId: " + f.getDocenteId());
        }

        // 👉 refrescar la tabla
        data.setAll(formatos);
    }

    @FXML
    private void initialize() {
        // Configurar columna (solo el título)
        colTitulo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));

        // Vincular tabla a la lista observable
        tbFormatos.setItems(data);

 
        tbFormatos.setRowFactory(tv -> {
            TableRow<FormatoA> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    FormatoA seleccionado = row.getItem();
                    System.out.println("Seleccionado: " + seleccionado.getId() +
                            " - " + seleccionado.getTitulo());
                }
            });

            row.setOnMousePressed(event -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: lightgray;"); // efecto presionado
                }
            });

            row.setOnMouseReleased(event -> {
                row.setStyle(""); // volver al estilo normal
            });

            return row;
        });


    }
    @FXML
    void EventSalir(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/loggedDocente.fxml"));
            Parent root = loader.load();

            LoggedDocenteController docenteController = loader.getController();
            docenteController.setController(authController);
            docenteController.setFormatoAController(formatoAController); // 👈 agregar esto

            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
