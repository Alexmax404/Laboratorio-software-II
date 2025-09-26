package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IFormatoARepositorio;
import co.unicauca.gestiontg.domain.SubmitResult;
import co.unicauca.gestiontg.domain.EnumModalidad;
import co.unicauca.gestiontg.domain.FormatoA;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class ServicioFormatoA {

    private final IFormatoARepositorio formatoRepo;

    public ServicioFormatoA(IFormatoARepositorio formatoRepo) {
        this.formatoRepo = formatoRepo;
    }

    public SubmitResult crearOReenviarFormato(UUID formatoId, UUID estudianteId1, UUID estudianteId2, UUID docenteId, UUID enviadoPor, String titulo, EnumModalidad modalidad, String director, String coDirector, LocalDate fechaPresentacion, String objetivosGenerales, String objetivosEspecificos, String archivoFormatoPath) throws Exception {
        validarCamposObligatorios(titulo, director, fechaPresentacion, objetivosGenerales, objetivosEspecificos, archivoFormatoPath);
        try {
            return formatoRepo.submitFormato(
                    formatoId,
                    estudianteId1,
                    estudianteId2,
                    docenteId,
                    enviadoPor,
                    titulo,
                    modalidad.name(),
                    director,
                    coDirector,
                    java.sql.Date.valueOf(fechaPresentacion),
                    objetivosGenerales,
                    objetivosEspecificos,
                    archivoFormatoPath
            );
        } catch (Exception e) {
            throw  new Exception("Error: "+e);
        }
    }

    private void validarCamposObligatorios(String titulo, String director, LocalDate fechaPresentacion, String objetivosGenerales, String objetivosEspecificos, String archivoFormatoPath) throws Exception {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("El título es obligatorio");
        }
        if (director == null || director.trim().isEmpty()) {
            throw new Exception("El director es obligatorio");
        }
        if (fechaPresentacion == null) {
            throw new Exception("La fecha de presentación es obligatoria");
        }
        if (objetivosGenerales == null || objetivosGenerales.trim().isEmpty()) {
            throw new Exception("El objetivo general es obligatorio");
        }
        if (objetivosEspecificos == null || objetivosEspecificos.trim().isEmpty()) {
            throw new Exception("Los objetivos específicos son obligatorios");
        }
        if (archivoFormatoPath == null || archivoFormatoPath.trim().isEmpty()) {
            throw new Exception("El archivo del formato es obligatorio");
        }
    }

    public List<FormatoA> obtenerFormatosPorEstudiante(UUID estudianteId) throws Exception {
        try {
            return formatoRepo.findFormatosByEstudianteId(estudianteId);
        } catch (Exception e) {
            throw new Exception("Error al obtener formatos: " + e.getMessage());
        }
    }
}
