package co.unicauca.gestiontg.controller;

import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.infra.Subject;
import co.unicauca.gestiontg.service.ServicioUsuario;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author kthn1
 */
public class AuthController extends Subject {
    
    private final ServicioUsuario userService;
    
    public AuthController(ServicioUsuario userService) {
        this.userService = userService;
    }
    
    public boolean registerUser(Usuario usuario) throws SQLException {
        return userService.register(usuario);
    }
    
    public boolean loginUser(String correo, String contrasenia) throws SQLException {
        return userService.login(correo, contrasenia);
    }
    
    public Optional<String> getRolUsuario(String correo) throws SQLException {
        return userService.obtenerRolUsuario(correo);
    }
    
    public String validarCorreoInstitucional(String correo) {
        return userService.validarCorreoInstitucional(correo);
    }
    
    public String validarContrasenia(String contrasenia) {
        return userService.validarContrasenia(contrasenia);
    }
    
    public Usuario getUsuarioPorEstudianteCorreo(String correo) {
        try {
            return userService.obtenerUsuarioPorEstudianteCorreo(correo)
                    .orElseThrow(() -> new RuntimeException("No existe usuario para este estudiante"));
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            return null;
        }
    }
    
}
