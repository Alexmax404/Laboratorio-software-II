package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.EnumEstadoFormato;
import static co.unicauca.gestiontg.domain.EnumEstadoFormato.Aprobado;
import static co.unicauca.gestiontg.domain.EnumEstadoFormato.En_Revision;
import static co.unicauca.gestiontg.domain.EnumEstadoFormato.Rechazado;
import co.unicauca.gestiontg.domain.FormatoA;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ListaDeFormatosCoordinadorController {

    @FXML
    private Button btnExit;

    @FXML
    private TableColumn<FormatoA, String> colEstado;

    @FXML
    private TableColumn<FormatoA, String> colTitulo;

    @FXML
    private Pane pnDatos1;

    @FXML
    private TableView<FormatoA> tbFormatos;

    private FormatoAController formatoAController;
    private AuthController authController;
    private ObservableList<FormatoA> data = FXCollections.observableArrayList();

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

    public void setFormatoAController(FormatoAController formatoAController) {
        this.formatoAController = formatoAController;
    }

    public void setController(AuthController authController) throws SQLException {
        this.authController = authController;

        // Obtener formatos desde el controlador
        List<FormatoA> formatos = formatoAController.listarFormatos();

        // DEBUG
        data.setAll(formatos);
        tbFormatos.setItems(data);
        tbFormatos.refresh();
    }

    @FXML
    public void initialize() {

        // Título
        colTitulo.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getTitulo())
        );

        // Estado
        colEstado.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getEstado().name())
        );

        // Estilo para la columna Estado
        colEstado.setCellFactory(column -> new TableCell<FormatoA, String>() {
            private final Label label = new Label();

            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);

                if (empty || estado == null) {
                    setGraphic(null);
                } else {
                    label.setText(estado);

                    // Estilo por estado
                    switch (EnumEstadoFormato.valueOf(estado)) {
                        case Aprobado:
                            label.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 4px 8px; -fx-background-radius: 12;");
                            break;
                        case Rechazado:
                            label.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 4px 8px; -fx-background-radius: 12;");
                            break;
                        case En_Revision:
                            label.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-padding: 4px 8px; -fx-background-radius: 12;");
                            break;
                        default:
                            label.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: black; -fx-padding: 4px 8px; -fx-background-radius: 12;");
                            break;
                    }

                    setGraphic(label);
                }
            }
        });

        tbFormatos.setRowFactory(tv -> {
            TableRow<FormatoA> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // 👈 1 clic (puedes poner == 2 para doble clic)
                    FormatoA formatoSeleccionado = row.getItem();
                    try {
                        switchToCorregirFormato(formatoSeleccionado);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                    // 🔹 Aquí defines lo que quieres hacer
                    System.out.println("Clic en formato: " + formatoSeleccionado.getTitulo());
                }
            });
            return row;
        });

        // Enlazar data
        tbFormatos.setItems(data);
    }

    private void switchToCorregirFormato(FormatoA formato) throws IOException, SQLException {
        // Cargar la vista corregirFormato.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/unicauca/gestiontg/corregirFormato.fxml"));
        Parent root = loader.load();

        // Obtener el controlador
        CorregirFormatoController controller = loader.getController();

        // Pasar los datos del formato seleccionado
        controller.setFormato(formato);
        controller.setController(authController);
        controller.setFormatoAController(formatoAController);
        // Mostrar en la misma ventana (Stage actual)
        Stage stage = (Stage) tbFormatos.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

}
