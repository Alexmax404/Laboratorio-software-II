package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.infra.Subject;
import java.sql.SQLException;

public class Servicio extends Subject{
    
    private IUsuarioRepositorio repositorio;
    private Usuario ultimoUsuario; //el último usuario registrado
    
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
        String mensaje="";
        
        if (contrasenia == null || contrasenia.isBlank()) {
            mensaje += "La contraseña no puede estar vacía\n";
        }
        if (contrasenia.length() < 6) {
            mensaje += "La contraseña debe tener al menos 6 caracteres\n";
        }
        if (!contrasenia.matches(".*[A-Z].*")) {
            mensaje += "La contraseña debe contener al menos una letra mayuscula\n";
        }
        if (!contrasenia.matches(".*\\d.*")) {
            mensaje += "La contraseña debe contener al menos un numero\n";
        }
        if (!contrasenia.matches(".*[!@#$%^&*().,_-].*")) {
            mensaje += "La contraseña debe contener al menos un caracter especial\n";
        }
        return mensaje.isEmpty() ? "OK" : mensaje;
    }
    
    public boolean registrarUsuario(Usuario nuevoUsuario) throws SQLException{
    
        if(nuevoUsuario == null || nuevoUsuario.getNombres() == null){
            return false;
        }  
        
        boolean registrado = repositorio.registrarUsuario(nuevoUsuario);
        
        if (registrado) {
            this.ultimoUsuario = nuevoUsuario;
            notifyAllObserves(); 
        }

        return registrado;
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

    public Usuario getUltimoUsuario() {
        return ultimoUsuario;
    }

    
}