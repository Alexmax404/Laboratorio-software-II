package co.unicauca.gestiontg.access;
import co.unicauca.gestiontg.domain.*;

public interface IUsuarioRepositorio {
    boolean registrarUsuario(Usuario nuevoUsuario);
    boolean iniciarSesion(Usuario usuario);
    
}
