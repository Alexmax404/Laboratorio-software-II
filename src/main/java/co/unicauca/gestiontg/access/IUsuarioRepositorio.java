package co.unicauca.gestiontg.access;
import co.unicauca.gestiontg.domain.*;
import java.sql.Connection;
import java.sql.SQLException;

public interface IUsuarioRepositorio {
    boolean registrarUsuario(Usuario nuevoUsuario) throws SQLException;
    boolean iniciarSesion(String correo, String contrasenia);
    boolean buscarCorreo(Connection conn, String correo) throws SQLException;
    Usuario obtenerUsuarioPorCorreo(String correo);   
    String obtenerRolUsuario(String email);
}