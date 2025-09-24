package co.unicauca.gestiontg.access;

import co.unicauca.gestiontg.config.DatabaseConfig;
import co.unicauca.gestiontg.domain.EnumDecision;
import co.unicauca.gestiontg.domain.EvaluacionCoordinador;
import java.sql.Connection;
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
public class EvaluacionCoordinadorRepositorio implements IEvaluacionCoordinadorRepositorio {

    @Override
    public UUID saveEvaluacionCoordinador(EvaluacionCoordinador eval) throws SQLException {
        String sql = "SELECT * FROM gtg.add_evaluacion_coordinador(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, eval.getFormatoId(), Types.OTHER);
            if (eval.getFormatoVersionId() != null) {
                stmt.setObject(2, eval.getFormatoVersionId(), Types.OTHER);
            } else {
                stmt.setNull(2, Types.OTHER);
            }
            stmt.setObject(3, eval.getCoordinadorId(), Types.OTHER);
            stmt.setString(4, eval.getDecision().name());
            stmt.setString(5, eval.getComentarios());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // la función declara OUT o_eval_id; lo más seguro es leer por índice 1
                    Object obj = rs.getObject(1);
                    if (obj instanceof UUID) {
                        return (UUID) obj;
                    } else if (obj instanceof java.util.UUID) {
                        return (UUID) obj;
                    } else {
                        // en algunos drivers se devuelve String
                        return UUID.fromString(obj.toString());
                    }
                }
            }
        }
        throw new SQLException("No se recibió id al insertar evaluación coordinador");
    }

    @Override
    public Optional<EvaluacionCoordinador> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM gtg.evaluacion_coordinador WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id, Types.OTHER);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapFromResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<EvaluacionCoordinador> findByFormatoId(UUID formatoId) throws SQLException {
        String sql = "SELECT * FROM gtg.evaluacion_coordinador WHERE formato_id = ? ORDER BY fecha_evaluacion DESC";
        List<EvaluacionCoordinador> lista = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, formatoId, Types.OTHER);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapFromResultSet(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public boolean existsByFormatoVersionAndCoordinador(UUID formatoId, UUID formatoVersionId, UUID coordinadorId) throws SQLException {
        String sql = "SELECT 1 FROM gtg.evaluacion_coordinador WHERE formato_id = ? AND formato_version_id = ? AND coordinador_id = ? LIMIT 1";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, formatoId, Types.OTHER);
            if (formatoVersionId != null) {
                stmt.setObject(2, formatoVersionId, Types.OTHER);
            } else {
                stmt.setNull(2, Types.OTHER);
            }
            stmt.setObject(3, coordinadorId, Types.OTHER);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private EvaluacionCoordinador mapFromResultSet(ResultSet rs) throws SQLException {
        EvaluacionCoordinador evaluacion = new EvaluacionCoordinador();
        evaluacion.setId((UUID) rs.getObject("id"));
        evaluacion.setFormatoId((UUID) rs.getObject("formato_id"));
        evaluacion.setFormatoVersionId((UUID) rs.getObject("formato_version_id"));
        evaluacion.setCoordinadorId((UUID) rs.getObject("coordinador_id"));
        evaluacion.setDecision(EnumDecision.valueOf(rs.getString("decision")));
        evaluacion.setComentarios(rs.getString("comentarios"));
        evaluacion.setFechaEvaluacion(rs.getTimestamp("fecha_evaluacion").toLocalDateTime());
        return evaluacion;
    }
}
