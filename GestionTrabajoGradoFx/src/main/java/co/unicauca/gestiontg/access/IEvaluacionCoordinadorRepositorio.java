package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.domain.EvaluacionCoordinador;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public interface IEvaluacionCoordinadorRepositorio {

    UUID saveEvaluacionCoordinador(EvaluacionCoordinador eval) throws SQLException;

    Optional<EvaluacionCoordinador> findById(UUID id) throws SQLException;

    List<EvaluacionCoordinador> findByFormatoId(UUID formatoId) throws SQLException;
    
    boolean existsByFormatoVersionAndCoordinador(UUID formatoId, UUID formatoVersionId, UUID coordinadorId) throws SQLException;
}
