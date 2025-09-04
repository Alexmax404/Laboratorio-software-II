package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.domain.*;
import co.unicauca.gestiontg.service.Servicio;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioRepositorio implements IUsuarioRepositorio {
    
    private Connection conn;
    private static final String URL = "jdbc:sqlite:./GestionTG.db";
    
    public UsuarioRepositorio() {
        connect();
        initDatabase();
    }
    
    @Override
    public boolean registrarUsuario(Usuario nuevoUsuario) throws SQLException {
        String contrasenaHash = BCrypt.hashpw(nuevoUsuario.getContrasenia(), BCrypt.gensalt());
        String sql = "INSERT INTO Usuario VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            if (buscarCorreo(conn, nuevoUsuario.getCorreo())) {
                return false;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoUsuario.getNombres());
                pstmt.setString(2, nuevoUsuario.getApellidos());
                pstmt.setString(3, nuevoUsuario.getCelular()); 
                pstmt.setString(4, nuevoUsuario.getPrograma().toString());
                pstmt.setString(5, nuevoUsuario.getRol().toString());
                pstmt.setString(6, nuevoUsuario.getCorreo());
                pstmt.setString(7, contrasenaHash);
                pstmt.executeUpdate();
            }
            System.out.println("Usuario registrado.");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    @Override
    public boolean iniciarSesion(String correo, String contrasenia) {
        try {
            String sql = "SELECT contrasenia FROM Usuario WHERE correo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, correo);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    String contraseniaSegura = rs.getString("contrasenia");
                    System.out.println(contrasenia);
                    return BCrypt.checkpw(contrasenia, contraseniaSegura);
                }
            }
        } catch(SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }            
        
        return false;
    }
    
    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS Usuario (\n"
                + "	nombres text NOT NULL,\n"
                + "	apellidos text NOT NULL,\n"
                + "	celular text,\n"
                + "	programa text NOT NULL CHECK (programa IN ('IngenieriaDeSistemas', 'IngenieriaElectronicaYTelecomunicaciones', 'AutomaticaIndustrial', 'TecnologiaIndustrial')),\n"
                + "	rol text NOT NULL CHECK (rol IN ('Docente', 'Estudiante')),\n"
                + "	correo text PRIMARY KEY,\n"
                + "	contrasenia text NOT NULL \n"                 
                + ");";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                conn.setAutoCommit(true); // opcional, para evitar locks prolongados
            }
        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String obtenerRolUsuario(String correo) {
        String sql = "SELECT rol FROM Usuario WHERE correo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public boolean buscarCorreo(Connection conn, String correo) throws SQLException {
        String sqlValidacion = "SELECT correo FROM Usuario WHERE correo = ?";
        try (PreparedStatement statement = conn.prepareStatement(sqlValidacion)) {
            statement.setString(1, correo);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }
     
    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM Usuario WHERE correo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("celular"),
                            EnumPrograma.valueOf(rs.getString("programa")),
                            EnumRol.valueOf(rs.getString("rol")),
                            rs.getString("correo"),
                            null
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
