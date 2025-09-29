package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.EnumEstadoFormato;
import static co.unicauca.gestiontg.domain.EnumEstadoFormato.Aprobado;
import static co.unicauca.gestiontg.domain.EnumEstadoFormato.En_Revision;
import static co.unicauca.gestiontg.domain.EnumEstadoFormato.Rechazado;
import co.unicauca.gestiontg.domain.FormatoA;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class ListaDeEstadosEstudianteController {

    @FXML
    private Button btnExit;

    @FXML
    private TableColumn<FormatoA, String> colDirector;

    @FXML
    private TableColumn<FormatoA, String> colEstado;

    @FXML
    private TableColumn<FormatoA, String> colFecha;

    @FXML
    private TableColumn<FormatoA, String> colId;

    @FXML
    private TableColumn<FormatoA, String> colTitulo;

    @FXML
    private Pane pnDatos1;
    
    private FormatoAController formatoAController;
    private AuthController authController;
    private ObservableList<FormatoA> data = FXCollections.observableArrayList();
    @FXML
    private TableView<FormatoA> tbFormatos;
    public void setFormatoAController(FormatoAController formatoAController) {
        this.formatoAController = formatoAController;
    }

    public void setController(AuthController authController) throws SQLException {
        this.authController = authController;

        // Obtener formatos desde el controlador
        List<FormatoA> formatos = formatoAController.listarFormatosByUsuario(authController.getUsuarioLogueado().getId().toString());
        data.setAll(formatos);
        tbFormatos.setItems(data);
        tbFormatos.refresh();
    }
    @FXML
    void EventSalir(ActionEvent event) {
        
    }
    @FXML
    public void initialize() {
        // ID
        colId.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getId().toString())
        );

        // Título
        colTitulo.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getTitulo())
        );

        // Director
        colDirector.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getDirector())
        );

        // Fecha
        colFecha.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getFechaPresentacion();
            return new SimpleStringProperty(fecha != null ? fecha.toString() : "");
        });

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

    }
}
