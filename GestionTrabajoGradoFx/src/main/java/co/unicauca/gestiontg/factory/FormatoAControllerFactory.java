package co.unicauca.gestiontg.factory;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.infra.Subject;
import co.unicauca.gestiontg.service.ServicioFormatoA;

/**
 *
 * @author kthn1
 */
public class FormatoAControllerFactory {

    public co.unicauca.gestiontg.controller.FormatoAController create() {
        return new co.unicauca.gestiontg.controller.FormatoAController(
                new ServicioFormatoA(new FormatoARepositorio(), new Subject())
        );
    }
    
}
