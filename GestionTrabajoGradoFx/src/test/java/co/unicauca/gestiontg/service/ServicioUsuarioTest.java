package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.domain.EnumPrograma;
import co.unicauca.gestiontg.domain.EnumRol;
import co.unicauca.gestiontg.domain.Usuario;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class ServicioUsuarioTest {

    @Mock
    IUsuarioRepositorio repo;

    ServicioUsuario service;
    AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        service = Mockito.spy(new ServicioUsuario(repo));
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    // -------- Validaciones --------
    @Test
    void validarCorreoInstitucional_ok() {
        assertEquals("OK",
            service.validarCorreoInstitucional("alguien@unicauca.edu.co"));
    }

    @Test
    void validarCorreoInstitucional_invalido() {
        String r = service.validarCorreoInstitucional("otro@gmail.com");
        assertTrue(r.contains("@unicauca.edu.co"));
    }

    @Test
    void validarContrasenia_reglasBasicas() {
        assertEquals("OK", service.validarContrasenia("Aa1!23"));

        String r = service.validarContrasenia("aaaaaa");
        assertNotEquals("OK", r);
        assertTrue(r.contains("mayuscula"));
        assertTrue(r.contains("numero"));
        assertTrue(r.contains("especial"));
    }

    // -------- register() --------
    @Test
    void register_hasheaYGuarda_yNotifica() throws SQLException {
        Usuario u = new Usuario("Ana","García","3120000000",
                EnumPrograma.IngenieriaDeSistemas, EnumRol.Estudiante,
                "ana@unicauca.edu.co", "ClaveSegura1!");

    
        doNothing().when(service).notifyAllObserves();

        // Verifica que se guarda un hash, no texto plano
        doAnswer(inv -> {
            Usuario capt = inv.getArgument(0);
            assertNotEquals("ClaveSegura1!", capt.getContrasenia());
            assertTrue(capt.getContrasenia().startsWith("$2")); // BCrypt
            return null;
        }).when(repo).save(any(Usuario.class));

        assertTrue(service.register(u));
        verify(repo, times(1)).save(any(Usuario.class));
        verify(service, times(1)).notifyAllObserves();
    }

    // -------- login() --------
    @Test
    void login_ok_conHash() throws SQLException {
        String plano = "MiClave123!";
        String hash = BCrypt.hashpw(plano, BCrypt.gensalt());
        Usuario almacenado = new Usuario("Ana","G","",
                  EnumPrograma.IngenieriaDeSistemas, EnumRol.Estudiante,
                "ana@unicauca.edu.co", hash);

        when(repo.findByCorreo("ana@unicauca.edu.co"))
                .thenReturn(Optional.of(almacenado));

        assertTrue(service.login("ana@unicauca.edu.co", plano));
        assertNotNull(service.getUsuarioLogueado());
        assertEquals("ana@unicauca.edu.co", service.getUsuarioLogueado().getCorreo());
    }

    @Test
    void login_falla_usuarioInexistente() throws SQLException {
        when(repo.findByCorreo("nadie@unicauca.edu.co"))
                .thenReturn(Optional.empty());
        assertFalse(service.login("nadie@unicauca.edu.co", "x"));
        assertNull(service.getUsuarioLogueado());
    }

    @Test
    void login_falla_hashNoCoincide() throws SQLException {
        String hash = BCrypt.hashpw("otraClave", BCrypt.gensalt());
        Usuario u = new Usuario("A","B","",
                EnumPrograma.IngenieriaDeSistemas, EnumRol.Estudiante,
                "ana@unicauca.edu.co", hash);

        when(repo.findByCorreo("ana@unicauca.edu.co"))
                .thenReturn(Optional.of(u));

        assertFalse(service.login("ana@unicauca.edu.co", "MiClave123!"));
    }
}
