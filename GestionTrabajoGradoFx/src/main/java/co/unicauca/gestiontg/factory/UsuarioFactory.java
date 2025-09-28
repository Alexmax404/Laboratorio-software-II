package co.unicauca.gestiontg.factory;

import co.unicauca.gestiontg.domain.EnumPrograma;
import co.unicauca.gestiontg.domain.EnumRol;
import co.unicauca.gestiontg.domain.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kthn1
 */
public class UsuarioFactory {

    public static Usuario fromResultSet(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId((java.util.UUID) rs.getObject("id"));
        u.setNombres(rs.getString("nombres"));
        u.setApellidos(rs.getString("apellidos"));
        u.setCelular(rs.getString("celular"));
        u.setPrograma(EnumPrograma.valueOf(rs.getString("programa")));
        u.setRol(EnumRol.valueOf(rs.getString("rol")));
        u.setCorreo(rs.getString("correo"));
        u.setContrasenia(rs.getString("contrasenia"));
        return u;
    }
}
