package co.unicauca.gestiontg.domain;

import java.util.UUID;

public class Usuario {

    private UUID id;
    private String nombres;
    private String apellidos;
    private String celular;
    private EnumPrograma programa;
    private EnumRol rol;
    private String correo;
    private String contrasenia;

    public Usuario(String nombres, String apellidos, String celular, EnumPrograma programa, EnumRol rol, String correo, String contrasenia) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.programa = programa;
        this.rol = rol;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public Usuario() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getNombreCompleto() {
        return nombres+" "+apellidos;
    }
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public EnumPrograma getPrograma() {
        return programa;
    }

    public void setPrograma(EnumPrograma programa) {
        this.programa = programa;
    }

    public EnumRol getRol() {
        return rol;
    }

    public void setRol(EnumRol rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
