package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.domain.SubmitResult;
import co.unicauca.gestiontg.config.DatabaseConfig;
import co.unicauca.gestiontg.domain.EnumEstadoFormato;
import co.unicauca.gestiontg.domain.EnumModalidad;
import co.unicauca.gestiontg.domain.FormatoA;
import co.unicauca.gestiontg.domain.FormatoAVersion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class FormatoARepositorio implements IFormatoARepositorio {

    @Override
    public SubmitResult submitFormato(UUID formatoId, UUID estudianteId1, UUID estudianteId2, UUID docenteId, UUID enviadoPor, String titulo, String modalidad, String director, String coDirector, Date fechaPresentacion, String objetivosGenerales, String objetivosEspecificos, String archivoFormatoPath) throws Exception {
        String sql = "SELECT * FROM gtg.submit_formato(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, formatoId, Types.OTHER);
            stmt.setObject(2, estudianteId1, Types.OTHER);
            stmt.setObject(3, estudianteId2, Types.OTHER);
            stmt.setObject(4, docenteId, Types.OTHER);
            stmt.setObject(5, enviadoPor, Types.OTHER);
            stmt.setString(6, titulo);
            stmt.setString(7, modalidad);
            stmt.setString(8, director);
            stmt.setString(9, coDirector);
            stmt.setDate(10, fechaPresentacion);
            stmt.setString(11, objetivosGenerales);
            stmt.setString(12, objetivosEspecificos);
            stmt.setString(13, archivoFormatoPath);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UUID oFormatoId = (UUID) rs.getObject("o_formato_id");
                    int oVersion = rs.getInt("o_version");
                    return new SubmitResult(oFormatoId, oVersion);
                }
            }
        }
        throw new Exception("Error ejecutando submit_formato");
    }

    @Override
    public List<FormatoA> findFormatosByUsuario(UUID usuarioId) throws SQLException {
        String sql = "SELECT * FROM gtg.formato WHERE estudiante_id1 = ? OR estudiante_id2 = ? OR docente_id = ? ORDER BY created_at DESC";

        List<FormatoA> formatos = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, usuarioId, Types.OTHER);
            stmt.setObject(2, usuarioId, Types.OTHER);
            stmt.setObject(3, usuarioId, Types.OTHER);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FormatoA formato = mapFormatoFromResultSet(rs);
                    formatos.add(formato);
                }
            }
        }

        return formatos;
    }

    @Override
    public boolean existsById(UUID formatoId) throws SQLException {
        String sql = "SELECT 1 FROM gtg.formato WHERE id = ? LIMIT 1";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, formatoId, Types.OTHER);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public boolean existsVersionById(UUID formatoVersionId) throws SQLException {
        String sql = "SELECT 1 FROM gtg.formato_version WHERE id = ? LIMIT 1";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, formatoVersionId, Types.OTHER);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<FormatoA> listarFormatos() throws SQLException {
        String sql = "SELECT id, titulo, fecha_presentacion, estado, director FROM gtg.formato";
        List<FormatoA> formatos = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FormatoA formato = new FormatoA();
                formato.setId(UUID.fromString(rs.getString("id")));
                formato.setTitulo(rs.getString("titulo"));
                formato.setFechaPresentacion(rs.getDate("fecha_presentacion").toLocalDate());
                formato.setEstado(EnumEstadoFormato.valueOf(rs.getString("estado")));
                formato.setDirector(rs.getString("director"));
                formatos.add(formato);
            }
        }
        return formatos;
    }

    @Override
    public Optional<FormatoAVersion> obtenerDetalleFormato(UUID formatoId) throws SQLException {
        String sql = "SELECT fv.id, fv.version, fv.titulo, fv.modalidad, fv.director, fv.co_director, "
                + "fv.fecha_presentacion, fv.objetivos_generales, fv.objetivos_especificos, "
                + "fv.archivo_formato_path, fv.estado_local, fv.enviado_por "
                + "FROM gtg.formato_version fv WHERE fv.formato_id = ? "
                + "ORDER BY fv.version DESC LIMIT 1";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, formatoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FormatoAVersion v = new FormatoAVersion();
                    v.setId(UUID.fromString(rs.getString("id")));
                    v.setVersion(rs.getInt("version"));
                    v.setTitulo(rs.getString("titulo"));
                    v.setModalidad(EnumModalidad.valueOf(rs.getString("modalidad")));
                    v.setDirector(rs.getString("director"));
                    v.setCoDirector(rs.getString("co_director"));
                    v.setFechaPresentacion(rs.getDate("fecha_presentacion").toLocalDate());
                    v.setObjetivosGenerales(rs.getString("objetivos_generales"));
                    v.setObjetivosEspecificos(rs.getString("objetivos_especificos"));
                    v.setArchivoFormatoPath(rs.getString("archivo_formato_path"));
                    v.setEstadoLocal(EnumEstadoFormato.valueOf(rs.getString("estado_local")));
                    v.setEnviadoPor(UUID.fromString(rs.getString("enviado_por")));
                    return Optional.of(v);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    public Optional<List<String>> obtenerNombresEstudiantesPorFormatoId(UUID formatoId) throws SQLException {
        String sql = "SELECT "
                + "COALESCE(u1.nombres || ' ' || u1.apellidos, '') AS estudiante1, "
                + "COALESCE(u2.nombres || ' ' || u2.apellidos, '') AS estudiante2 "
                + "FROM gtg.formato f "
                + "LEFT JOIN gtg.usuario u1 ON f.estudiante_id1 = u1.id "
                + "LEFT JOIN gtg.usuario u2 ON f.estudiante_id2 = u2.id "
                + "WHERE f.id = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, formatoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    List<String> nombres = new ArrayList<>();
                    String estudiante1 = rs.getString("estudiante1");
                    String estudiante2 = rs.getString("estudiante2");

                    if (estudiante1 != null && !estudiante1.isBlank()) {
                        nombres.add(estudiante1.trim());
                    }
                    if (estudiante2 != null && !estudiante2.isBlank()) {
                        nombres.add(estudiante2.trim());
                    }

                    return Optional.of(nombres);
                }
            }
        }
        return Optional.empty();
    }

    public UUID obtenerFormatoVersionPorIDFormato(UUID formatoId) throws SQLException {
        String sql = "SELECT fv.id "
                + "FROM gtg.formato f "
                + "INNER JOIN gtg.formato_version fv ON f.id = fv.formato_id "
                + "WHERE f.id = ? "
                + "ORDER BY fv.version DESC LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, formatoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return (UUID) rs.getObject("id");
                } else {
                    throw new SQLException("No se encontró formato_version para el formato con id " + formatoId);
                }
            }
        }
    }

    private FormatoA mapFormatoFromResultSet(ResultSet rs) throws SQLException {
        FormatoA formato = new FormatoA();
        formato.setId((UUID) rs.getObject("id"));
        formato.setEstudianteId1((UUID) rs.getObject("estudiante_id1"));
        formato.setEstudianteId2((UUID) rs.getObject("estudiante_id2"));
        formato.setDocenteId((UUID) rs.getObject("docente_id"));
        formato.setTitulo(rs.getString("titulo"));
        formato.setModalidad(EnumModalidad.valueOf(rs.getString("modalidad")));
        formato.setDirector(rs.getString("director"));
        formato.setCoDirector(rs.getString("co_director"));
        formato.setFechaPresentacion(rs.getDate("fecha_presentacion").toLocalDate());
        formato.setEstado(EnumEstadoFormato.valueOf(rs.getString("estado")));
        formato.setIntentos(rs.getInt("intentos"));
        formato.setMaxIntentos(rs.getInt("max_intentos"));
        formato.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        formato.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return formato;
    }
}
