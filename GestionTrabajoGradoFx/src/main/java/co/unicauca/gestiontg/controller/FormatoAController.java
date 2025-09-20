package co.unicauca.gestiontg.controller;

import co.unicauca.gestiontg.domain.EnumModalidad;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.service.ServicioFormatoA;
import java.time.LocalDate;
import java.util.List;
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

    public String crearOReenviarFormato(String formatoIdStr, String estudianteId1Str, String estudianteId2Str, String docenteIdStr, String enviadoPorStr, String titulo, String modalidadStr, String director, String coDirector, String fechaPresentacionStr, String objetivosGenerales, String objetivosEspecificos, String cartaAceptacionPath, String archivoFormatoPath) {
        try {
            UUID formatoId = formatoIdStr != null && !formatoIdStr.isEmpty() ? UUID.fromString(formatoIdStr) : null;
            UUID estudianteId1 = UUID.fromString(estudianteId1Str);
            UUID estudianteId2 = (estudianteId2Str != null && !estudianteId2Str.isEmpty()) ? UUID.fromString(estudianteId2Str) : null;
            UUID docenteId = UUID.fromString(docenteIdStr);
            UUID enviadoPor = UUID.fromString(enviadoPorStr);
            EnumModalidad modalidad = EnumModalidad.valueOf(modalidadStr);
            LocalDate fechaPresentacion = LocalDate.parse(fechaPresentacionStr);

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
                    cartaAceptacionPath,
                    archivoFormatoPath
            );
            return "Éxito. Formato ID: " + result.getFormatoId() + ", Versión: " + result.getVersion();
        } catch (Exception e) {
            return "Error inesperado: " + e.getMessage();
        }
    }
    
    public List<FormatoA> listarFormatosDeEstudiante(String estudianteIdStr) {
        try {
            UUID estudianteId = UUID.fromString(estudianteIdStr);
            return servicio.obtenerFormatosPorEstudiante(estudianteId);
        } catch (Exception e) {
            System.err.println("Error de negocio: " + e.getMessage());
            return List.of(); // Devuelve lista vacía en caso de error
        } 
    }
}
