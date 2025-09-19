package co.unicauca.gestiontg.access;

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
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class FormatoARepositorio implements IFormatoARepositorio {

    @Override
    public SubmitResult submitFormato(UUID formatoId, UUID estudianteId1, UUID estudianteId2, UUID enviadoPor, String titulo, String modalidad, String director, String coDirector, Date fechaPresentacion, String objetivosGenerales, String objetivosEspecificos, String cartaAceptacionPath, String archivoFormatoPath) throws Exception {
        String sql = "SELECT * FROM gtg.submit_formato(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, formatoId, Types.OTHER);
            stmt.setObject(2, estudianteId1, Types.OTHER);
            stmt.setObject(3, estudianteId2, Types.OTHER);
            stmt.setObject(4, enviadoPor, Types.OTHER);
            stmt.setString(5, titulo);
            stmt.setString(6, modalidad);
            stmt.setString(7, director);
            stmt.setString(8, coDirector);
            stmt.setDate(9, fechaPresentacion);
            stmt.setString(10, objetivosGenerales);
            stmt.setString(11, objetivosEspecificos);
            stmt.setString(12, cartaAceptacionPath);
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
    public FormatoA findFormatoById(UUID id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FormatoAVersion> findVersionsByFormatoId(UUID formatoId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FormatoA> findFormatosByEstudianteId(UUID estudianteId) throws SQLException {
        String sql = "SELECT * FROM gtg.formato WHERE estudiante_id1 = ? OR estudiante_id2 = ? ORDER BY created_at DESC";

        List<FormatoA> formatos = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, estudianteId, Types.OTHER);
            stmt.setObject(2, estudianteId, Types.OTHER);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FormatoA formato = mapFormatoFromResultSet(rs);
                    formatos.add(formato);
                }
            }
        }

        return formatos;
    }

    private FormatoA mapFormatoFromResultSet(ResultSet rs) throws SQLException {
        FormatoA formato = new FormatoA();
        formato.setId((UUID) rs.getObject("id"));
        formato.setEstudianteId1((UUID) rs.getObject("estudiante_id1"));
        formato.setEstudianteId2((UUID) rs.getObject("estudiante_id2"));
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
