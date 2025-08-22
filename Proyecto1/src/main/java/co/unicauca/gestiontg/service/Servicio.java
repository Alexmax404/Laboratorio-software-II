package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.domain.Usuario;
import java.sql.SQLException;

public class Servicio {
    private IUsuarioRepositorio repositorio;

    public Servicio(IUsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    public String validarCorreoInstitucional(String correo) {
        // Verifica que termine con el dominio institucional
        if (!correo.endsWith("@unicauca.edu.co")) {
            return "El correo debe pertenecer al dominio @unicauca.edu.co.";
        }

        return "OK";
    }
    
    public String validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.isBlank()) {
            return "La contraseña no puede estar vacía";
        }
        if (contrasenia.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres";
        }
        
        if (!contrasenia.matches(".*[A-Z].*")) {
            return "La contraseña debe contener al menos una letra mayuscula";
        }
        if (!contrasenia.matches(".*\\d.*")) {
            return "La contraseña debe contener al menos un numero";
        }
        if (!contrasenia.matches(".*[!@#$%^&*().,_-].*")) {
            return "La contraseña debe contener al menos un caracter especial";
        }
        return "OK";
    }
    
    public boolean registrarUsuario(Usuario nuevoUsuario) throws SQLException{
    
        if(nuevoUsuario == null || nuevoUsuario.getNombres() == null){
            return false;
        }       
        return repositorio.registrarUsuario(nuevoUsuario); 
    }
    
    public boolean inicioSesion(String correo, String contrasenia){
        if(repositorio.iniciarSesion(correo, contrasenia)){
            return true;
        }
        return false;
    }
    public Usuario obtenerUsuarioPorCorreo(String correo){
        Usuario usuario = repositorio.obtenerUsuarioPorCorreo(correo);
        return usuario;        
    }
    public String obtenerRolUsuario(String correo) {
        return repositorio.obtenerRolUsuario(correo);
    }

    
}