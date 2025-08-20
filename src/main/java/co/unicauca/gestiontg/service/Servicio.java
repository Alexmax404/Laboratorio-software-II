
package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.domain.Usuario;
import java.sql.SQLException;

/**
 *
 * @author Nicolas
 */
public class Servicio {
    private IUsuarioRepositorio repositorio;

    public Servicio(IUsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    public String validarCorreoInstitucional(String correo) {
//        if (correo == null || correo.trim().isEmpty()) {
//            return "El correo no puede estar vacío.";
//        }
        if (!correo.endsWith("@unicauca.edu.co")) {
            return "El correo debe ser institucional (@unicauca.edu.co)";
        }
        return "OK";
    }
    
    public String validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.isBlank()) {
            return "La contrasenia no puede estar vacía";
        }
        if (contrasenia.length() < 6) {
            return "La contrasenia debe tener al menos 6 caracteres";
        }
        
        if (!contrasenia.matches(".*[A-Z].*")) {
            return "La contrasenia debe contener al menos una letra mayuscula";
        }
        if (!contrasenia.matches(".*\\d.*")) {
            return "La contrasenia debe contener al menos un numero";
        }
        if (!contrasenia.matches(".*[!@#$%^&*().,_-].*")) {
            return "La contrasenia debe contener al menos un caracter especial";
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
}
