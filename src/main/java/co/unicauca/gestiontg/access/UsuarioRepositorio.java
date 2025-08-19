package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.domain.*;
import co.unicauca.gestiontg.service.Servicio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioRepositorio implements IUsuarioRepositorio {
    
    private Connection conn;

    public UsuarioRepositorio() {
        initDatabase();
    }
    
    @Override
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        String contraseniaSegura = BCrypt.hashpw(nuevoUsuario.getContrasenia(), BCrypt.gensalt());
        
        try{
            //this.connect();
            String sql = "INSERT INTO Usuario VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nuevoUsuario.getNombres());
            pstmt.setString(2, nuevoUsuario.getApellidos());
            pstmt.setInt(3, nuevoUsuario.getCelular());
            pstmt.setString(4, nuevoUsuario.getPrograma().toString());
            pstmt.setString(5, nuevoUsuario.getRol().toString());
            pstmt.setString(6, nuevoUsuario.getCorreo());
            pstmt.setString(7, contraseniaSegura);
            pstmt.executeUpdate();
            
            System.out.println("Usuario registrado.");
            return true;
        } catch(SQLException ex){
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    @Override
    public boolean iniciarSesion(Usuario usuario) {
        try{
            String sql = "SELECT contrasenia FROM Usuario WHERE correo = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario.getCorreo());
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                String contraseniaSegura = rs.getString("contrasenia");                
                return BCrypt.checkpw(usuario.getContrasenia(), contraseniaSegura);
            }
            
        } catch(SQLException ex){
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }            
        
        return false;
    }

    
    
    private void initDatabase() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Usuario (\n"
                + "	nombres text NOT NULL,\n"
                + "	apellidos text NOT NULL,\n"
                + "	celular int,\n"
                + "	programa text NOT NULL CHECK (programa IN ('IngenieriaDeSistemas', 'IngenieriaElectronicaYTelecomunicaciones', 'AutomaticaIndustrial', 'TecnologiaIndustrial')),\n"
                + "	rol text NOT NULL CHECK (rol IN ('Docente', 'Estudiante')),\n"
                + "	correo text PRIMARY KEY,\n"
                + "	contrasenia text NOT NULL \n"                 
                + ");";

        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:./GestionTG.db";
        //String url = "jdbc:sqlite::memory:";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    
}
