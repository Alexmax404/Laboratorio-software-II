package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IFormatoARepositorio;
import co.unicauca.gestiontg.access.SubmitResult;
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

    public SubmitResult crearOReenviarFormato(UUID formatoId, UUID estudianteId1, UUID estudianteId2, UUID enviadoPor, String titulo, EnumModalidad modalidad, String director, String coDirector, LocalDate fechaPresentacion, String objetivosGenerales, String objetivosEspecificos, String cartaAceptacionPath, String archivoFormatoPath) throws Exception {
        validarCamposObligatorios(titulo, director, fechaPresentacion, objetivosGenerales, objetivosEspecificos, archivoFormatoPath);
        try {
            return formatoRepo.submitFormato(
                    formatoId,
                    estudianteId1,
                    estudianteId2,
                    enviadoPor,
                    titulo,
                    modalidad.name(),
                    director,
                    coDirector,
                    java.sql.Date.valueOf(fechaPresentacion),
                    objetivosGenerales,
                    objetivosEspecificos,
                    cartaAceptacionPath,
                    archivoFormatoPath
            );
        } catch (Exception e) {
            throw mapearExcepcion(e);
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

    private Exception mapearExcepcion(Exception e) {
        String msg = e.getMessage();
        if (msg.contains("numero maximo de intentos")) {
            return new Exception("Se alcanzó el máximo de 3 intentos");
        } else if (msg.contains("propietario") || msg.contains("p_enviado_por")) {
            return new Exception("No tiene permisos para modificar este formato");
        } else if (msg.contains("no existe")) {
            return new Exception("Estudiante no encontrado");
        }
        return new Exception("Error en el envío: " + msg);
    }

    public List<FormatoA> obtenerFormatosPorEstudiante(UUID estudianteId) throws Exception {
        try {
            return formatoRepo.findFormatosByEstudianteId(estudianteId);
        } catch (Exception e) {
            throw new Exception("Error al obtener formatos: " + e.getMessage());
        }
    }
}
