package co.unicauca.gestiontg.controller;

import co.unicauca.gestiontg.domain.EnumDecision;
import co.unicauca.gestiontg.service.ServicioEvaluacionCoordinador;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class EvaluacionCoordinadorController {

    private final ServicioEvaluacionCoordinador servicio;

    public EvaluacionCoordinadorController(ServicioEvaluacionCoordinador servicio) {
        this.servicio = servicio;
    }

    public String evaluar(String formatoIdStr, String formatoVersionIdStr, String coordinadorIdStr, String decisionStr, String comentarios) {
        try {
            UUID formatoId = UUID.fromString(formatoIdStr);
            UUID formatoVersionId = (formatoVersionIdStr == null || formatoVersionIdStr.isBlank()) ? null : UUID.fromString(formatoVersionIdStr);
            UUID coordinadorId = UUID.fromString(coordinadorIdStr);

            EnumDecision decision = EnumDecision.valueOf(decisionStr); // espera Aprobado/Correcciones/Rechazado (nombre exacto del enum)
            UUID evalId = servicio.evaluarFormato(formatoId, formatoVersionId, coordinadorId, decision, comentarios);
            return "Evaluación registrada con id: " + evalId;
        } catch (Exception ex) {
            return "Error inesperado: " + ex.getMessage();
        }
    }
}
