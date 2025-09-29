package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.domain.SubmitResult;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.domain.FormatoAVersion;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public interface IFormatoARepositorio {

    SubmitResult submitFormato(
            UUID formatoId,
            UUID estudianteId1,
            UUID estudianteId2,
            UUID docenteId,
            UUID enviadoPor,
            String titulo,
            String modalidad,
            String director,
            String coDirector,
            java.sql.Date fechaPresentacion,
            String objetivosGenerales,
            String objetivosEspecificos,
            String archivoFormatoPath
    ) throws Exception;
    
    List<FormatoA> findFormatosByUsuario(UUID usuarioId) throws SQLException;

    boolean existsById(UUID formatoId) throws SQLException;
    
    boolean existsVersionById(UUID formatoVersionId) throws SQLException;
    
    List<FormatoA> listarFormatos() throws SQLException; 
    
    Optional<FormatoAVersion> obtenerDetalleFormato(UUID formatoId) throws SQLException;
    
    public UUID obtenerFormatoVersionPorIDFormato(UUID formatoId) throws SQLException;
    
}
