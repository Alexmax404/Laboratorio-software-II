package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IEvaluacionCoordinadorRepositorio;
import co.unicauca.gestiontg.access.IFormatoARepositorio;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.domain.EnumDecision;
import co.unicauca.gestiontg.domain.EvaluacionCoordinador;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class ServicioEvaluacionCoordinador {

    private final IEvaluacionCoordinadorRepositorio evalRepo;
    private final IFormatoARepositorio formatoRepo;

    public ServicioEvaluacionCoordinador(IEvaluacionCoordinadorRepositorio evalRepo, IFormatoARepositorio formatoRepo) {
        this.evalRepo = evalRepo;
        this.formatoRepo = formatoRepo;
    }

    public UUID evaluarFormato(UUID formatoId, UUID formatoVersionId, UUID coordinadorId, EnumDecision decision, String comentarios) throws SQLException, Exception {
        // Validar existencia y pertencencia de  de la version
        boolean formatoVersionExiste = formatoRepo.existsVersionById(formatoVersionId);
        try {
            formatoVersionExiste = formatoRepo.existsVersionById(formatoVersionId);
        } catch (SQLException e) {
            throw new Exception("Error consultando formato", e);
        }
        if (!formatoVersionExiste) throw new Exception("Formato " + formatoVersionId + " no existe");

        // No permitir evaluación doble por mismo coordinador/version
        boolean yaExisteEval = evalRepo.existsByFormatoVersionAndCoordinador(formatoId, formatoVersionId, coordinadorId);
        if (yaExisteEval) {
            throw new Exception("Ya existe una evaluación de este coordinador para ese formato/version");
        }

        // Construir entidad y llamar repo
        EvaluacionCoordinador eval = new EvaluacionCoordinador(formatoId, formatoVersionId, coordinadorId, decision, comentarios);
        try {
            UUID evalId = evalRepo.saveEvaluacionCoordinador(eval);
            return evalId;
        } catch (SQLException e) {
            throw new Exception("Error guardando evaluación en la BD: " + e.getMessage(), e);
        }
    }
}
