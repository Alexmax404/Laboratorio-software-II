package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.events.DomainEvent;
import co.unicauca.gestiontg.events.EnumEventType;
import co.unicauca.gestiontg.infra.Subject;
import java.sql.SQLException;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

public class ServicioUsuario {

    private final IUsuarioRepositorio userRepository;

    private Usuario usuarioLogueado;

    private final Subject eventPublisher;

    public ServicioUsuario(IUsuarioRepositorio userRepository, Subject eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public String validarCorreoInstitucional(String correo) {
        // Verifica que termine con el dominio institucional
        if (!correo.endsWith("@unicauca.edu.co")) {
            return "El correo debe pertenecer al dominio @unicauca.edu.co.";
        }

        return "OK";
    }

    public String validarContrasenia(String contrasenia) {
        String mensaje = "";

        if (contrasenia == null || contrasenia.isBlank()) {
            mensaje += "La contraseña no puede estar vacía\n";
        }
        if (contrasenia.length() < 6) {
            mensaje += "La contraseña debe tener al menos 6 caracteres\n";
        }
        if (!contrasenia.matches(".*[A-Z].*")) {
            mensaje += "La contraseña debe contener al menos una letra mayuscula\n";
        }
        if (!contrasenia.matches(".*\\d.*")) {
            mensaje += "La contraseña debe contener al menos un numero\n";
        }
        if (!contrasenia.matches(".*[!@#$%^&*().,_-].*")) {
            mensaje += "La contraseña debe contener al menos un caracter especial\n";
        }
        return mensaje.isEmpty() ? "OK" : mensaje;
    }

    public String validarUsuarioPorCorreo(String correo) throws SQLException {
        userRepository.findByCorreo(correo);
        if (userRepository.findByCorreo(correo).isEmpty()) {
            return "El correo del usuario no existe.";
        }
        return "OK";
    }

    // Nuevoooo
    public boolean register(Usuario usuario) throws SQLException {
        // Encriptar y guardar
        String hashed = BCrypt.hashpw(usuario.getContrasenia(), BCrypt.gensalt());
        usuario.setContrasenia(hashed);
        userRepository.save(usuario);

        // Notificar
        eventPublisher.notifyObservers(new DomainEvent(EnumEventType.USER_REGISTERED, usuario));
        return true;
    }

    public boolean login(String correo, String contrasenia) throws SQLException {
        Optional<Usuario> optUser = userRepository.findByCorreo(correo);
        if (optUser.isPresent()) {
            Usuario user = optUser.get();
            if (BCrypt.checkpw(contrasenia, user.getContrasenia())) {
                usuarioLogueado = user;
                eventPublisher.notifyObservers(new DomainEvent(EnumEventType.LOGIN_EXITOSO, correo));
                return true;
            } else {
                eventPublisher.notifyObservers(new DomainEvent(EnumEventType.LOGIN_FALLIDO, correo));
            }
        }
        return false;
    }

    public void logout() {
        if (usuarioLogueado != null) {
            String correo = usuarioLogueado.getCorreo();
            eventPublisher.notifyObservers(new DomainEvent(EnumEventType.LOGOUT, correo));
            usuarioLogueado = null;
        } else {
            eventPublisher.notifyObservers(new DomainEvent(EnumEventType.LOGOUT, null));
        }
    }

    public Subject getEventPublisher() {
        return eventPublisher;
    }

    public Optional<String> obtenerRolUsuario(String correo) throws SQLException {
        return userRepository.getRolByCorreo(correo);
    }

    public Optional<Usuario> obtenerUsuarioPorEstudianteCorreo(String correo) throws SQLException {
        return userRepository.findByCorreo(correo);
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

}
