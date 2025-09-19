package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.domain.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface IUsuarioRepositorio {

    void save(Usuario usuario) throws SQLException;

    Optional<Usuario> findByCorreo(String correo) throws SQLException;

    boolean existsByCorreo(String correo) throws SQLException;

    Optional<String> getRolByCorreo(String correo) throws SQLException;
}
