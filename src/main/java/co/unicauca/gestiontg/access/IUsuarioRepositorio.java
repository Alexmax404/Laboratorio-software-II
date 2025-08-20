package co.unicauca.gestiontg.access;
import co.unicauca.gestiontg.domain.*;
import java.sql.Connection;
import java.sql.SQLException;

public interface IUsuarioRepositorio {
    boolean registrarUsuario(Usuario nuevoUsuario);
    boolean iniciarSesion(String correo, String contrasenia);
    boolean buscarEmail(Connection conn, String email) throws SQLException;
    Usuario obtenerUsuarioPorEmail(String email);   
}
