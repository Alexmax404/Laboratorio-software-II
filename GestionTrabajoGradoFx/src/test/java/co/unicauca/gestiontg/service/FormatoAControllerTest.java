package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.EnumModalidad;
import co.unicauca.gestiontg.domain.SubmitResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests para FormatoAController.
 * IMPORTANTE: el servicio lanza Exception, por eso los métodos de test
 * declaran "throws Exception".
 */
@ExtendWith(MockitoExtension.class)
class FormatoAControllerTest {

    @Mock
    private ServicioFormatoA servicio;

    @InjectMocks
    private FormatoAController controller;

    private final EnumModalidad mod = EnumModalidad.values()[0];

    @Test
    @DisplayName("OK: crear NUEVO (formatoId = null) y est2 en blanco -> null")
    void crearNuevo_ok_est2Vacio() throws Exception {
        UUID est1 = UUID.randomUUID();
        UUID docente = UUID.randomUUID();
        UUID enviadoPor = UUID.randomUUID();
        LocalDate fecha = LocalDate.of(2025, 10, 1);

        SubmitResult result = mock(SubmitResult.class);
        UUID generado = UUID.randomUUID();
        when(result.getFormatoId()).thenReturn(generado);
        when(result.getVersion()).thenReturn(1);

        when(servicio.crearOReenviarFormato(
                isNull(),
                eq(est1),
                isNull(),
                eq(docente),
                eq(enviadoPor),
                eq("Titulo"),
                eq(mod),
                eq("Dir"),
                eq("CoDir"),
                eq(fecha),
                eq("ObjG"),
                eq("ObjE"),
                eq("/f.pdf")
        )).thenReturn(result);

        String out = controller.crearOReenviarFormato(
                null,
                est1.toString(),
                "",
                docente.toString(),
                enviadoPor.toString(),
                "Titulo",
                mod.name(),
                "Dir",
                "CoDir",
                fecha,
                "ObjG",
                "ObjE",
                "/f.pdf"
        );

        assertEquals("Éxito. Formato ID: " + generado + ", Versión: 1", out);

        verify(servicio).crearOReenviarFormato(
                isNull(), eq(est1), isNull(), eq(docente), eq(enviadoPor),
                eq("Titulo"), eq(mod), eq("Dir"), eq("CoDir"),
                eq(fecha), eq("ObjG"), eq("ObjE"), eq("/f.pdf")
        );
        verifyNoMoreInteractions(servicio);
    }

    @Test
    @DisplayName("OK: REENVIAR (formatoId != null) con ambos estudiantes")
    void reenviar_ok_conDosEstudiantes() throws Exception {
        UUID formatoId = UUID.randomUUID();
        UUID est1 = UUID.randomUUID();
        UUID est2 = UUID.randomUUID();
        UUID docente = UUID.randomUUID();
        UUID enviadoPor = UUID.randomUUID();
        LocalDate fecha = LocalDate.of(2025, 9, 1);

        SubmitResult result = mock(SubmitResult.class);
        when(result.getFormatoId()).thenReturn(formatoId);
        when(result.getVersion()).thenReturn(3);

        when(servicio.crearOReenviarFormato(
                eq(formatoId),
                eq(est1),
                eq(est2),
                eq(docente),
                eq(enviadoPor),
                eq("Titulo"),
                eq(mod),
                eq("Dir"),
                eq("CoDir"),
                eq(fecha),
                eq("ObjG"),
                eq("ObjE"),
                eq("/tmp/formatoA.pdf")
        )).thenReturn(result);

        String out = controller.crearOReenviarFormato(
                formatoId.toString(),
                est1.toString(),
                est2.toString(),
                docente.toString(),
                enviadoPor.toString(),
                "Titulo",
                mod.name(),
                "Dir",
                "CoDir",
                fecha,
                "ObjG",
                "ObjE",
                "/tmp/formatoA.pdf"
        );

        assertEquals("Éxito. Formato ID: " + formatoId + ", Versión: 3", out);

        verify(servicio).crearOReenviarFormato(
                eq(formatoId), eq(est1), eq(est2), eq(docente), eq(enviadoPor),
                eq("Titulo"), eq(mod), eq("Dir"), eq("CoDir"),
                eq(fecha), eq("ObjG"), eq("ObjE"), eq("/tmp/formatoA.pdf")
        );
        verifyNoMoreInteractions(servicio);
    }

    @Test
    @DisplayName("ERROR: el servicio lanza excepción y el controller responde con mensaje")
    void crearOReenviar_errorEnServicio() throws Exception {
        UUID est1 = UUID.randomUUID();
        UUID docente = UUID.randomUUID();
        UUID enviadoPor = UUID.randomUUID();
        LocalDate fecha = LocalDate.of(2025, 8, 15);

        when(servicio.crearOReenviarFormato(
                isNull(),
                eq(est1),
                isNull(),
                eq(docente),
                eq(enviadoPor),
                anyString(),
                eq(mod),
                anyString(),
                anyString(),
                eq(fecha),
                anyString(),
                anyString(),
                anyString()
        )).thenThrow(new RuntimeException("Falla BD"));

        String out = controller.crearOReenviarFormato(
                null,
                est1.toString(),
                "",
                docente.toString(),
                enviadoPor.toString(),
                "Titulo",
                mod.name(),
                "Dir",
                "CoDir",
                fecha,
                "ObjG",
                "ObjE",
                "/f.pdf"
        );

        assertEquals("Error inesperado: Falla BD", out);

        verify(servicio).crearOReenviarFormato(
                isNull(), eq(est1), isNull(), eq(docente), eq(enviadoPor),
                eq("Titulo"), eq(mod), eq("Dir"), eq("CoDir"),
                eq(fecha), eq("ObjG"), eq("ObjE"), eq("/f.pdf")
        );
        verifyNoMoreInteractions(servicio);
    }
}
