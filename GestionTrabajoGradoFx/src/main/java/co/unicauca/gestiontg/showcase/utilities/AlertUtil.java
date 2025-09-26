package co.unicauca.gestiontg.showcase.utilities;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author kthn1
 */
public class AlertUtil {

    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo != null ? titulo : "Mensaje");
        alerta.setHeaderText(null);

        // Personalización del mensaje
        Label etiqueta = new Label(mensaje);
        etiqueta.setWrapText(true);

        VBox contenedor = new VBox(etiqueta);
        contenedor.setSpacing(10);
        contenedor.setPadding(new Insets(10));

        alerta.getDialogPane().setContent(contenedor);

        // Estilo general
        alerta.getDialogPane().setStyle(
                "-fx-background-color: #f9f9f9; "
                + "-fx-border-color: #ABBEF6; "
                + "-fx-border-width: 1px; "
                + "-fx-border-radius: 5px; "
                + "-fx-background-radius: 5px;"
        );

        // Estilo de botón
        alerta.getDialogPane().lookupButton(ButtonType.OK)
                .setStyle("-fx-background-color: #1E2C9E; "
                        + "-fx-text-fill: white; "
                        + "-fx-font-weight: bold; "
                        + "-fx-background-radius: 7px;");

        alerta.showAndWait();
    }
}
