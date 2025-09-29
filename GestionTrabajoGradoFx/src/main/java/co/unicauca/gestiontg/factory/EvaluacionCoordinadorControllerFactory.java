package co.unicauca.gestiontg.factory;

import co.unicauca.gestiontg.access.EvaluacionCoordinadorRepositorio;
import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.infra.Subject;
import co.unicauca.gestiontg.service.ServicioEvaluacionCoordinador;

/**
 *
 * @author kthn1
 */
public class EvaluacionCoordinadorControllerFactory {

    public co.unicauca.gestiontg.controller.EvaluacionCoordinadorController create() {
        return new co.unicauca.gestiontg.controller.EvaluacionCoordinadorController(
                new ServicioEvaluacionCoordinador(
                        new EvaluacionCoordinadorRepositorio(),
                        new FormatoARepositorio(),
                        new Subject()
                )
        );
    }
}
