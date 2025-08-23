module com.mycompany.gestiontrabajogradofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.gestiontrabajogradofx to javafx.fxml;
    exports com.mycompany.gestiontrabajogradofx;
}
