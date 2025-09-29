package co.unicauca.gestiontg.events;

import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.events.DomainEvent;
import static co.unicauca.gestiontg.events.EnumEventType.LOGIN_EXITOSO;
import static co.unicauca.gestiontg.events.EnumEventType.LOGIN_FALLIDO;
import static co.unicauca.gestiontg.events.EnumEventType.LOGOUT;
import static co.unicauca.gestiontg.events.EnumEventType.USER_REGISTERED;
import co.unicauca.gestiontg.infra.Observer;
import co.unicauca.gestiontg.showcase.utilities.AlertUtil;
import javafx.scene.control.Alert;

/**
 *
 * @author kthn1
 */
public class AlertObserver implements Observer {

    @Override
    public void update(DomainEvent event) {
        switch (event.getName()) {
            case USER_REGISTERED:
                Usuario u = (Usuario) event.getData();
                AlertUtil.mostrarAlerta("Usuario Registrado", "Bienvenido " + u.getNombreCompleto(), Alert.AlertType.INFORMATION);
                break;
            case LOGIN_FALLIDO:
                AlertUtil.mostrarAlerta("Error", "Credenciales incorrectas", Alert.AlertType.WARNING);
                break;
            case LOGIN_EXITOSO:
                AlertUtil.mostrarAlerta("Bienvenido", "Ingreso exitoso", Alert.AlertType.INFORMATION);
                break;
            case LOGOUT:
                AlertUtil.mostrarAlerta("Despedida", "Salida exitosa", Alert.AlertType.INFORMATION);
                break;
            case FORMATO_CREADO:
                AlertUtil.mostrarAlerta("Creación", "Formato creado", Alert.AlertType.INFORMATION);

            default:
                throw new AssertionError();
        }
    }
}
