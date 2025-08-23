package com.mycompany.gestiontrabajogradofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class PrimaryController {

    @FXML
    private Button btnBoton;

    @FXML
    void switchToSecondary(ActionEvent event) throws IOException {
        App.setRoot("secondary");
        System.out.println("HOLAAAAAAAAAAAAAA");
    }

}
