package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.config.DatabaseConfig;
import co.unicauca.gestiontg.domain.*;
import co.unicauca.gestiontg.factory.UsuarioFactory;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class UsuarioRepositorio implements IUsuarioRepositorio {

    private Connection conn;

    public UsuarioRepositorio() {
    }

    @Override
    public void save(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO gtg.usuario (nombres, apellidos, celular, programa, rol, correo, contrasenia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombres());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getCelular());
            stmt.setString(4, usuario.getPrograma().name());
            stmt.setString(5, usuario.getRol().name());
            stmt.setString(6, usuario.getCorreo());
            stmt.setString(7, usuario.getContrasenia());
            stmt.executeUpdate();
        }
    }

    public Optional<Usuario> findByCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM gtg.usuario WHERE correo = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(UsuarioFactory.fromResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByCorreo(String correo) throws SQLException {
        String sql = "SELECT 1 FROM gtg.usuario WHERE correo = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Optional<String> getRolByCorreo(String correo) throws SQLException {
        String sql = "SELECT rol FROM gtg.usuario WHERE correo = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(rs.getString("rol"));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getRolById(UUID id) throws SQLException {
        String sql = "SELECT rol FROM gtg.usuario WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id, Types.OTHER);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(rs.getString("rol"));
                }
            }
        }
        return Optional.empty();
    }
}
