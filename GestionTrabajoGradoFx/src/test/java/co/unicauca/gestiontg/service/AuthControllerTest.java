package co.unicauca.gestiontg.service;
/**
 *
 * @author juliaaa
 */
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.service.ServicioUsuario;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private ServicioUsuario servicio;

    private AuthController controller;
    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        controller = new AuthController(servicio);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void registerUser_ok() throws SQLException {
        Usuario u = new Usuario("Ana","García","3000000000", null, null,
                "ana@unicauca.edu.co", "ClaveSegura1!");
        when(servicio.register(u)).thenReturn(true);

        assertTrue(controller.registerUser(u));
        verify(servicio).register(u);
    }

    @Test
    void loginUser_true_y_false() throws SQLException {
        when(servicio.login("ana@unicauca.edu.co", "MiClave123!")).thenReturn(true);
        assertTrue(controller.loginUser("ana@unicauca.edu.co", "MiClave123!"));

        when(servicio.login("ana@unicauca.edu.co", "x")).thenReturn(false);
        assertFalse(controller.loginUser("ana@unicauca.edu.co", "x"));
    }

    @Test
    void getRolUsuario_ok() throws SQLException {
        when(servicio.obtenerRolUsuario("ana@unicauca.edu.co"))
                .thenReturn(Optional.of("ESTUDIANTE"));

        assertEquals(Optional.of("ESTUDIANTE"),
                controller.getRolUsuario("ana@unicauca.edu.co"));
        verify(servicio).obtenerRolUsuario("ana@unicauca.edu.co");
    }

    @Test
    void validaciones_delegan() {
        when(servicio.validarCorreoInstitucional("ana@unicauca.edu.co")).thenReturn("OK");
        when(servicio.validarContrasenia("MiClave123!")).thenReturn("OK");

        assertEquals("OK", controller.validarCorreoInstitucional("ana@unicauca.edu.co"));
        assertEquals("OK", controller.validarContrasenia("MiClave123!"));
    }

    @Test
    void validarUsuarioPorCorreo_delega() throws SQLException {
        when(servicio.validarUsuarioPorCorreo("ana@unicauca.edu.co")).thenReturn("OK");
        assertEquals("OK", controller.validarUsuarioPorCorreo("ana@unicauca.edu.co"));
        verify(servicio).validarUsuarioPorCorreo("ana@unicauca.edu.co");
    }

    @Test
    void getUsuarioPorEstudianteCorreo_ok_y_noExiste_y_sqlError() throws SQLException {
        Usuario u = new Usuario("Ana","García","3000000000", null, null,
                "ana@unicauca.edu.co", "hash");

        when(servicio.obtenerUsuarioPorEstudianteCorreo("ana@unicauca.edu.co"))
                .thenReturn(Optional.of(u));
        assertNotNull(controller.getUsuarioPorEstudianteCorreo("ana@unicauca.edu.co"));

        when(servicio.obtenerUsuarioPorEstudianteCorreo("no@unicauca.edu.co"))
                .thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> controller.getUsuarioPorEstudianteCorreo("no@unicauca.edu.co"));
        assertTrue(ex.getMessage().contains("No existe usuario"));

        when(servicio.obtenerUsuarioPorEstudianteCorreo("err@unicauca.edu.co"))
                .thenThrow(new SQLException("falló la BD"));
        assertNull(controller.getUsuarioPorEstudianteCorreo("err@unicauca.edu.co"));
    }

    @Test
    void getUsuarioLogueado_delega() {
        Usuario u = new Usuario("Ana","García","3000000000", null, null,
                "ana@unicauca.edu.co", "hash");
        when(servicio.getUsuarioLogueado()).thenReturn(u);
        assertEquals("ana@unicauca.edu.co", controller.getUsuarioLogueado().getCorreo());
        verify(servicio).getUsuarioLogueado();
    }
}
