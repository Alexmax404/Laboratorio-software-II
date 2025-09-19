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
        String sqlUsuario = "INSERT INTO Usuario (nombres, apellidos, celular, programa, rol, correo, contrasenia) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            if (buscarCorreo(conn, nuevoUsuario.getCorreo())) {
                return false; // ya existe correo
            }

            // Insertar en tabla Usuario y recuperar id generado
            int idUsuarioGenerado = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, nuevoUsuario.getNombres());
                pstmt.setString(2, nuevoUsuario.getApellidos());
                pstmt.setString(3, nuevoUsuario.getCelular());
                pstmt.setString(4, nuevoUsuario.getPrograma().toString());
                pstmt.setString(5, nuevoUsuario.getRol().toString());
                pstmt.setString(6, nuevoUsuario.getCorreo());
                pstmt.setString(7, contrasenaHash);

                int filas = pstmt.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se pudo registrar el usuario en la tabla Usuario.");
                }

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idUsuarioGenerado = rs.getInt(1);
                    }
                }
            }

            // Dependiendo del rol, insertar en la tabla correspondiente
            String sqlExtra = null;
            switch (nuevoUsuario.getRol()) {
                case Docente -> sqlExtra = "INSERT INTO Docente (idDocente, correo) VALUES (?, ?)";
                case Estudiante -> sqlExtra = "INSERT INTO Estudiante (idEstudiante, correo) VALUES (?, ?)";
                case Coordinador -> sqlExtra = "INSERT INTO Coordinador (idCoordinador, correo) VALUES (?, ?)";
            }

            if (sqlExtra != null) {
                try (PreparedStatement pstmtExtra = conn.prepareStatement(sqlExtra)) {
                    pstmtExtra.setInt(1, idUsuarioGenerado);
                    pstmtExtra.setString(2, nuevoUsuario.getCorreo());
                    pstmtExtra.executeUpdate();
                }
            }

            System.out.println("Usuario registrado con ID: " + idUsuarioGenerado);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
            throw ex; // relanzamos para que la capa superior lo maneje
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
    
    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void initDatabase() {
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS Usuario (\n"
                + " idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nombres TEXT NOT NULL,\n"
                + " apellidos TEXT NOT NULL,\n"
                + " celular TEXT,\n"
                + " programa TEXT NOT NULL CHECK (programa IN "
                + "('IngenieriaDeSistemas', 'IngenieriaElectronicaYTelecomunicaciones', 'AutomaticaIndustrial', 'TecnologiaEnTelematica')),\n"
                + " rol TEXT NOT NULL CHECK (rol IN ('Docente','Estudiante','Coordinador')),\n"
                + " correo TEXT NOT NULL UNIQUE,\n"
                + " contrasenia TEXT NOT NULL\n"
                + ");";

        String sqlDocente = "CREATE TABLE IF NOT EXISTS Docente (\n"
                + " idDocente INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " correo TEXT NOT NULL UNIQUE,\n"
                + " FOREIGN KEY (idDocente) REFERENCES Usuario(idUsuario) ON DELETE CASCADE,\n"
                + " FOREIGN KEY (correo) REFERENCES Usuario(correo) ON DELETE CASCADE\n"
                + ");";

        String sqlEstudiante = "CREATE TABLE IF NOT EXISTS Estudiante (\n"
                + " idEstudiante INTEGER PRIMARY KEY,\n"
                + " correo TEXT NOT NULL UNIQUE,\n"
                + " FOREIGN KEY (idEstudiante) REFERENCES Usuario(idUsuario) ON DELETE CASCADE,\n"
                + " FOREIGN KEY (correo) REFERENCES Usuario(correo) ON DELETE CASCADE\n"
                + ");";

        String sqlCoordinador = "CREATE TABLE IF NOT EXISTS Coordinador (\n"
                + " idCoordinador INTEGER PRIMARY KEY,\n"
                + " correo TEXT NOT NULL UNIQUE,\n"
                + " FOREIGN KEY (idCoordinador) REFERENCES Usuario(idUsuario) ON DELETE CASCADE,\n"
                + " FOREIGN KEY (correo) REFERENCES Usuario(correo) ON DELETE CASCADE\n"
                + ");";
        
 
        String sqlFormatoA = "CREATE TABLE IF NOT EXISTS Proyecto (\n"
                + " idProyecto INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " titulo TEXT NOT NULL,\n"
                + " modalidad TEXT NOT NULL CHECK (modalidad IN ('Investigacion','PracticaProfesional')),\n"
                + " fechaCreacion TEXT NOT NULL,\n"
                + " idDocenteDirector INTEGER NOT NULL,\n"
                + " idDocenteCodirector INTEGER,\n"
                + " objetivoGeneral TEXT NOT NULL,\n"
                + " objetivosEspecificos TEXT NOT NULL,\n"
                + " archivoPDF TEXT NOT NULL,\n"
                + " estadoActual TEXT NOT NULL,\n"
                + " intentos INTEGER DEFAULT 0,\n"
                + " observaciones TEXT,\n"
                + " FOREIGN KEY(idDocenteDirector) REFERENCES Docente(idDocente),\n"
                + " FOREIGN KEY(idDocenteCodirector) REFERENCES Docente(idDocente)\n"
                + ");";

 
        String sqlEstudianteProyecto = "CREATE TABLE IF NOT EXISTS EstudianteProyecto (\n"
                + " idEstudiante INTEGER NOT NULL,\n"
                + " idProyecto INTEGER NOT NULL,\n"
                + " PRIMARY KEY (idEstudiante, idProyecto),\n"
                + " FOREIGN KEY(idEstudiante) REFERENCES Estudiante(idEstudiante) ON DELETE CASCADE,\n"
                + " FOREIGN KEY(idProyecto) REFERENCES Proyecto(idProyecto) ON DELETE CASCADE\n"
                + ");";


        String trgMaxEstudiantes = "CREATE TRIGGER IF NOT EXISTS trg_max_estudiantes_por_proyecto\n"
                + " BEFORE INSERT ON EstudianteProyecto\n"
                + " FOR EACH ROW\n"
                + " BEGIN\n"
                + "   SELECT CASE WHEN (\n"
                + "     (SELECT COUNT(*) FROM EstudianteProyecto WHERE idProyecto = NEW.idProyecto) >= 2\n"
                + "   ) THEN RAISE(ABORT, 'Un proyecto no puede tener más de 2 estudiantes') END;\n"
                + " END;";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsuario);
            stmt.execute(sqlDocente);
            stmt.execute(sqlEstudiante);
            stmt.execute(sqlCoordinador);
            stmt.execute(sqlFormatoA);
            stmt.execute(sqlEstudianteProyecto);
            stmt.execute(trgMaxEstudiantes);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
