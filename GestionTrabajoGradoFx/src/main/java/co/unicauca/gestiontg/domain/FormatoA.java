package co.unicauca.gestiontg.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class FormatoA {

    private UUID id;
    private UUID estudianteId1;
    private UUID estudianteId2;
    private UUID docenteId;
    private String titulo;
    private EnumModalidad modalidad;
    private String director;
    private String coDirector;
    private LocalDate fechaPresentacion;
    private EnumEstadoFormato estado;
    private int intentos;
    private int maxIntentos = 3;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FormatoA() {
    }

    public FormatoA(UUID id, UUID estudianteId1, UUID estudianteId2, UUID docenteId, String titulo, EnumModalidad modalidad, String director, String coDirector, LocalDate fechaPresentacion, EnumEstadoFormato estado, int intentos, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.estudianteId1 = estudianteId1;
        this.estudianteId2 = estudianteId2;
        this.docenteId = docenteId;
        this.titulo = titulo;
        this.modalidad = modalidad;
        this.director = director;
        this.coDirector = coDirector;
        this.fechaPresentacion = fechaPresentacion;
        this.estado = estado;
        this.intentos = intentos;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEstudianteId1() {
        return estudianteId1;
    }

    public void setEstudianteId1(UUID estudianteId1) {
        this.estudianteId1 = estudianteId1;
    }

    public UUID getEstudianteId2() {
        return estudianteId2;
    }

    public void setEstudianteId2(UUID estudianteId2) {
        this.estudianteId2 = estudianteId2;
    }

    public UUID getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(UUID docenteId) {
        this.docenteId = docenteId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public EnumModalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(EnumModalidad modalidad) {
        this.modalidad = modalidad;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCoDirector() {
        return coDirector;
    }

    public void setCoDirector(String coDirector) {
        this.coDirector = coDirector;
    }

    public LocalDate getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(LocalDate fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public EnumEstadoFormato getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoFormato estado) {
        this.estado = estado;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getMaxIntentos() {
        return maxIntentos;
    }

    public void setMaxIntentos(int maxIntentos) {
        this.maxIntentos = maxIntentos;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
