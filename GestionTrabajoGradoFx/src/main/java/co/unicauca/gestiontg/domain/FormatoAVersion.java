package co.unicauca.gestiontg.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class FormatoAVersion {

    private UUID id;
    private UUID formatoId;
    private int version;
    private String titulo;
    private EnumModalidad modalidad;
    private String director;
    private String coDirector;
    private LocalDate fechaPresentacion;
    private String objetivosGenerales;
    private String objetivosEspecificos;
    private String cartaAceptacionPath;
    private String archivoFormatoPath;
    private String observacionesEstudiante;
    private EnumEstadoFormato estadoLocal;
    private UUID enviadoPor;
    private LocalDateTime fechaSubida;
    
    public FormatoAVersion() {
    }

    public FormatoAVersion(UUID id, UUID formatoId, int version, String titulo, EnumModalidad modalidad, String director, String coDirector, LocalDate fechaPresentacion, String objetivosGenerales, String objetivosEspecificos, String cartaAceptacionPath, String archivoFormatoPath, String observacionesEstudiante, EnumEstadoFormato estadoLocal, UUID enviadoPor, LocalDateTime fechaSubida) {
        this.id = id;
        this.formatoId = formatoId;
        this.version = version;
        this.titulo = titulo;
        this.modalidad = modalidad;
        this.director = director;
        this.coDirector = coDirector;
        this.fechaPresentacion = fechaPresentacion;
        this.objetivosGenerales = objetivosGenerales;
        this.objetivosEspecificos = objetivosEspecificos;
        this.cartaAceptacionPath = cartaAceptacionPath;
        this.archivoFormatoPath = archivoFormatoPath;
        this.observacionesEstudiante = observacionesEstudiante;
        this.estadoLocal = estadoLocal;
        this.enviadoPor = enviadoPor;
        this.fechaSubida = fechaSubida;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public String getObjetivosGenerales() {
        return objetivosGenerales;
    }

    public void setObjetivosGenerales(String objetivosGenerales) {
        this.objetivosGenerales = objetivosGenerales;
    }

    public String getObjetivosEspecificos() {
        return objetivosEspecificos;
    }

    public void setObjetivosEspecificos(String objetivosEspecificos) {
        this.objetivosEspecificos = objetivosEspecificos;
    }

    public String getCartaAceptacionPath() {
        return cartaAceptacionPath;
    }

    public void setCartaAceptacionPath(String cartaAceptacionPath) {
        this.cartaAceptacionPath = cartaAceptacionPath;
    }

    public String getArchivoFormatoPath() {
        return archivoFormatoPath;
    }

    public void setArchivoFormatoPath(String archivoFormatoPath) {
        this.archivoFormatoPath = archivoFormatoPath;
    }

    public String getObservacionesEstudiante() {
        return observacionesEstudiante;
    }

    public void setObservacionesEstudiante(String observacionesEstudiante) {
        this.observacionesEstudiante = observacionesEstudiante;
    }

    public EnumEstadoFormato getEstadoLocal() {
        return estadoLocal;
    }

    public void setEstadoLocal(EnumEstadoFormato estadoLocal) {
        this.estadoLocal = estadoLocal;
    }

    public UUID getEnviadoPor() {
        return enviadoPor;
    }

    public void setEnviadoPor(UUID enviadoPor) {
        this.enviadoPor = enviadoPor;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}
