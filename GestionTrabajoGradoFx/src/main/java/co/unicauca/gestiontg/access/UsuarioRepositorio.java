package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.config.DatabaseConfig;
import co.unicauca.gestiontg.domain.*;
import co.unicauca.gestiontg.service.ServicioUsuario;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioRepositorio implements IUsuarioRepositorio {

    private Connection conn;

    public UsuarioRepositorio() {
    }

    // Nuevooo -------------------------------------------------------------------------------------------------------------------
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
                    Usuario u = new Usuario();
                    u.setId((java.util.UUID) rs.getObject("id"));
                    u.setNombres(rs.getString("nombres"));
                    u.setApellidos(rs.getString("apellidos"));
                    u.setCelular(rs.getString("celular"));
                    u.setPrograma(EnumPrograma.valueOf(rs.getString("programa")));
                    u.setRol(EnumRol.valueOf(rs.getString("rol")));
                    u.setCorreo(rs.getString("correo"));
                    u.setContrasenia(rs.getString("contrasenia"));
                    return Optional.of(u);
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

    // Se quedó. No lo he usado ---------------------------------------------------------------------------------------------
//    public String obtenerRolUsuario(String correo) {
//        String sql = "SELECT rol FROM Usuario WHERE correo = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, correo);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getString("rol");
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ServicioUsuario.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

//    @Override
//    public boolean buscarCorreo(Connection conn, String correo) throws SQLException {
//        String sqlValidacion = "SELECT correo FROM Usuario WHERE correo = ?";
//        try (PreparedStatement statement = conn.prepareStatement(sqlValidacion)) {
//            statement.setString(1, correo);
//            try (ResultSet rs = statement.executeQuery()) {
//                return rs.next();
//            }
//        }
//    }
    // Viejo 
//    public boolean registrarUsuario(Usuario nuevoUsuario) throws SQLException {
//        String contrasenaHash = BCrypt.hashpw(nuevoUsuario.getContrasenia(), BCrypt.gensalt());
//        String sql = "INSERT INTO Usuario VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//        try {
//            if (buscarCorreo(conn, nuevoUsuario.getCorreo())) {
//                return false;
//            }
//
//            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                stmt.setString(1, nuevoUsuario.getNombres());
//                stmt.setString(2, nuevoUsuario.getApellidos());
//                stmt.setString(3, nuevoUsuario.getCelular());
//                stmt.setString(4, nuevoUsuario.getPrograma().toString());
//                stmt.setString(5, nuevoUsuario.getRol().toString());
//                stmt.setString(6, nuevoUsuario.getCorreo());
//                stmt.setString(7, contrasenaHash);
//                stmt.executeUpdate();
//            }
//            System.out.println("Usuario registrado.");
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(ServicioUsuario.class.getName()).log(Level.SEVERE, null, ex);
//            throw ex;
//        }
//    }
//
//    public boolean iniciarSesion(String correo, String contrasenia) {
//        try {
//            String sql = "SELECT contrasenia FROM Usuario WHERE correo = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, correo);
//                ResultSet rs = pstmt.executeQuery();
//
//                if (rs.next()) {
//                    String contraseniaSegura = rs.getString("contrasenia");
//                    System.out.println(contrasenia);
//                    return BCrypt.checkpw(contrasenia, contraseniaSegura);
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ServicioUsuario.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return false;
//    }
//    
//        public Usuario obtenerUsuarioPorCorreo(String correo) {
//        String sql = "SELECT * FROM Usuario WHERE correo = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, correo);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    return new Usuario(
//                            rs.getString("nombres"),
//                            rs.getString("apellidos"),
//                            rs.getString("celular"),
//                            EnumPrograma.valueOf(rs.getString("programa")),
//                            EnumRol.valueOf(rs.getString("rol")),
//                            rs.getString("correo"),
//                            null
//                    );
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ServicioUsuario.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }  
}
