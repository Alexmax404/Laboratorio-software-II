package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IFormatoARepositorio;
import co.unicauca.gestiontg.domain.SubmitResult;
import co.unicauca.gestiontg.domain.EnumModalidad;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.domain.FormatoAVersion;
import co.unicauca.gestiontg.events.DomainEvent;
import co.unicauca.gestiontg.events.EnumEventType;
import co.unicauca.gestiontg.infra.Subject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class ServicioFormatoA {

    private final IFormatoARepositorio formatoRepo;

    private final Subject eventPublisher;

    public ServicioFormatoA(IFormatoARepositorio formatoRepo, Subject eventPublisher) {
        this.formatoRepo = formatoRepo;
        this.eventPublisher = eventPublisher;

    }

    public SubmitResult crearOReenviarFormato(UUID formatoId, UUID estudianteId1, UUID estudianteId2, UUID docenteId, UUID enviadoPor, String titulo, EnumModalidad modalidad, String director, String coDirector, LocalDate fechaPresentacion, String objetivosGenerales, String objetivosEspecificos, String archivoFormatoPath) throws Exception {
        validarCamposObligatorios(titulo, director, fechaPresentacion, objetivosGenerales, objetivosEspecificos, archivoFormatoPath);
        try {
            eventPublisher.notifyObservers(new DomainEvent(EnumEventType.FORMATO_CREADO, formatoId));
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
            throw new Exception("Error: " + e);
        }
    }

    public String obtenerNombreDePDF(UUID formatoId) throws SQLException {
        return formatoRepo.obtenerFormatoVersionPorIDFormato(formatoId).toString();
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

    public List<FormatoA> obtenerFormatosPorUsuario(UUID usuarioId) throws Exception {
        try {
            return formatoRepo.findFormatosByUsuario(usuarioId);
        } catch (Exception e) {
            throw new Exception("Error al obtener formatos: " + e.getMessage());
        }
    }

    public List<FormatoA> listarFormatos() {
        try {
            return formatoRepo.listarFormatos();
        } catch (SQLException e) {
            throw new RuntimeException("Error listando formatos", e);
        }
    }

    public Optional<FormatoAVersion> obtenerDetalles(UUID formatoId) {
        try {
            return formatoRepo.obtenerDetalleFormato(formatoId);
        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo detalle del formato", e);
        }
    }

    public Optional<List<String>> obtenerNombresEstudiantesPorFormatoId(UUID formatoId) throws SQLException {
        try {
            return formatoRepo.obtenerNombresEstudiantesPorFormatoId(formatoId);
        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo detalle del formato", e);
        }
    }

    public UUID obtenerFormatoVersionPorIDFormato(UUID formatoId) throws SQLException {
        try {
            return formatoRepo.obtenerFormatoVersionPorIDFormato(formatoId);
        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo detalle del formato", e);
        }
    }
}
