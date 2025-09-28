package co.unicauca.gestiontg.showcase.controller;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.FormatoA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElegirFormatoController {

    @FXML
    private Pane pnDatos1;

    @FXML
    private TableView<FormatoA> tbFormatos;

    @FXML
    private TableColumn<FormatoA, String> colTitulo;

    
    private List<FormatoA> formatos = new ArrayList<>();

    private AuthController authController;
    
    private FormatoAController formatoAController;
    
    public void setController(AuthController authController) throws SQLException {
        this.authController = authController;
        formatos = formatoAController.listarFormatosByUsuario(authController.getUsuarioLogueado().getId().toString());
    }
    @FXML
    private void initialize() {
        // Cargar datos desde el repositorio
        
        ObservableList<FormatoA> data = FXCollections.observableArrayList(formatos);
        // Configurar columna (solo el título)
        colTitulo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));
        tbFormatos.setItems(data);
        // Hacer filas clickeables
        tbFormatos.setRowFactory(tv -> {
            TableRow<FormatoA> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    FormatoA seleccionado = row.getItem();
                    System.out.println("Seleccionado: " + seleccionado.getId() + " - " + seleccionado.getTitulo());
                    // Aquí podrías abrir otra ventana con detalle o pasar el id
                }
            });
            return row;
        });
        // Ocultar encabezado
        tbFormatos.widthProperty().addListener((obs, oldVal, newVal) -> {
            Pane header = (Pane) tbFormatos.lookup("TableHeaderRow");
            if (header != null && header.isVisible()) {
                header.setMaxHeight(0);
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setVisible(false);
            }
        });
    }
}