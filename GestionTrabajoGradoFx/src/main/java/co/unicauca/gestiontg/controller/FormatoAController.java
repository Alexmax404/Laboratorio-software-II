package co.unicauca.gestiontg.controller;

import co.unicauca.gestiontg.domain.EnumModalidad;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.domain.FormatoAVersion;
import co.unicauca.gestiontg.service.ServicioFormatoA;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class FormatoAController {

    private final ServicioFormatoA servicio;

    public FormatoAController(ServicioFormatoA servicio) {
        this.servicio = servicio;
    }

    public String crearOReenviarFormato(
            String formatoIdStr,
            String estudianteId1Str,
            String estudianteId2Str,
            String docenteIdStr,
            String enviadoPorStr,
            String titulo,
            String modalidadStr,
            String director,
            String coDirector,
            LocalDate fechaPresentacion,
            String objetivosGenerales,
            String objetivosEspecificos,
            String archivoFormatoPath
    ) {
        try {
            UUID formatoId = formatoIdStr != null && !formatoIdStr.isEmpty() ? UUID.fromString(formatoIdStr) : null;
            UUID estudianteId1 = UUID.fromString(estudianteId1Str);
            UUID estudianteId2 = (estudianteId2Str != null && !estudianteId2Str.isEmpty()) ? UUID.fromString(estudianteId2Str) : null;
            UUID docenteId = UUID.fromString(docenteIdStr);
            UUID enviadoPor = UUID.fromString(enviadoPorStr);
            EnumModalidad modalidad = EnumModalidad.valueOf(modalidadStr);

            var result = servicio.crearOReenviarFormato(
                    formatoId,
                    estudianteId1,
                    estudianteId2,
                    docenteId,
                    enviadoPor,
                    titulo,
                    modalidad,
                    director,
                    coDirector,
                    fechaPresentacion,
                    objetivosGenerales,
                    objetivosEspecificos,
                    archivoFormatoPath
            );
            return "Éxito. Formato ID: " + result.getFormatoId() + ", Versión: " + result.getVersion();
        } catch (Exception e) {
            return "Error inesperado: " + e.getMessage();
        }
    }

    public String obtenerNombrePDF(UUID formatoId) throws SQLException {
        return servicio.obtenerNombreDePDF(formatoId);
    }

    public List<FormatoA> listarFormatosByUsuario(String usuarioIdStr) {
        try {
            UUID usuarioId = UUID.fromString(usuarioIdStr);
            return servicio.obtenerFormatosPorUsuario(usuarioId);
        } catch (Exception e) {
            System.err.println("Error de negocio: " + e.getMessage());
            return List.of();
        }
    }

    public List<FormatoA> listarFormatos() {
        return servicio.listarFormatos();
    }

    public Optional<FormatoAVersion> verDetalleFormato(UUID formatoId) {
        return servicio.obtenerDetalles(formatoId);
    }

    public Optional<List<String>> obtenerNombresEstudiantesPorFormatoId(UUID formatoId) throws SQLException {
        return servicio.obtenerNombresEstudiantesPorFormatoId(formatoId);
    }

    public UUID obtenerFormatoVersionPorIDFormato(UUID formatoId) throws SQLException {
        return servicio.obtenerFormatoVersionPorIDFormato(formatoId);
    }

    public String setObservaciones(UUID formatoVersionId, String observaciones) {
        try {
            boolean actualizado = servicio.setObservaciones(formatoVersionId, observaciones);
            if (actualizado) {
                return "Observaciones actualizadas correctamente.";
            } else {
                return "No se pudo actualizar, versión no encontrada.";
            }
        } catch (SQLException e) {
            return "Error al actualizar observaciones: " + e.getMessage();
        }
    }
}
