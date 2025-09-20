package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.domain.FormatoAVersion;
import java.util.List;
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
            String cartaAceptacionPath,
            String archivoFormatoPath
    ) throws Exception;

    FormatoA findFormatoById(UUID id) throws Exception;

    List<FormatoAVersion> findVersionsByFormatoId(UUID formatoId) throws Exception;

    List<FormatoA> findFormatosByEstudianteId(UUID estudianteId) throws Exception;
}
