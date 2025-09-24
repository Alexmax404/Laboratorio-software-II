package co.unicauca.gestiontg.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class EvaluacionCoordinador {

    private UUID id;
    private UUID formatoId;
    private UUID formatoVersionId; 
    private UUID coordinadorId;
    private EnumDecision decision;
    private String comentarios;
    private LocalDateTime fechaEvaluacion;

    public EvaluacionCoordinador() {
    }

    public EvaluacionCoordinador(UUID formatoId, UUID formatoVersionId, UUID coordinadorId, EnumDecision decision, String comentarios) {
        this.formatoId = formatoId;
        this.formatoVersionId = formatoVersionId;
        this.coordinadorId = coordinadorId;
        this.decision = decision;
        this.comentarios = comentarios;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFormatoId() {
        return formatoId;
    }

    public void setFormatoId(UUID formatoId) {
        this.formatoId = formatoId;
    }

    public UUID getFormatoVersionId() {
        return formatoVersionId;
    }

    public void setFormatoVersionId(UUID formatoVersionId) {
        this.formatoVersionId = formatoVersionId;
    }

    public UUID getCoordinadorId() {
        return coordinadorId;
    }

    public void setCoordinadorId(UUID coordinadorId) {
        this.coordinadorId = coordinadorId;
    }

    public EnumDecision getDecision() {
        return decision;
    }

    public void setDecision(EnumDecision decision) {
        this.decision = decision;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDateTime getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDateTime fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }
}
