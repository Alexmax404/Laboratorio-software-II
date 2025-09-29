package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.controller.EvaluacionCoordinadorController;
import co.unicauca.gestiontg.domain.EnumDecision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluacionCoordinadorControllerTest {

    @Mock
    private ServicioEvaluacionCoordinador servicio;

    @InjectMocks
    private EvaluacionCoordinadorController controller;

    @Test
    @DisplayName("OK: evalúa con versionId presente")
    void evaluar_ok_conVersion() throws Exception {
        UUID formatoId  = UUID.randomUUID();
        UUID versionId  = UUID.randomUUID();
        UUID coordId    = UUID.randomUUID();
        UUID esperado   = UUID.randomUUID();

        when(servicio.evaluarFormato(
                eq(formatoId),
                eq(versionId),
                eq(coordId),
                eq(EnumDecision.Aprobado),
                eq("Bien hecho")
        )).thenReturn(esperado);

        String out = controller.evaluar(
                formatoId.toString(),
                versionId.toString(),
                coordId.toString(),
                "Aprobado",
                "Bien hecho"
        );

        assertEquals("Evaluación registrada con id: " + esperado, out);
        verify(servicio).evaluarFormato(
                eq(formatoId),
                eq(versionId),
                eq(coordId),
                eq(EnumDecision.Aprobado),
                eq("Bien hecho")
        );
        verifyNoMoreInteractions(servicio);
    }

    @Test
    @DisplayName("OK: evalúa con versionId = null")
    void evaluar_ok_versionNull() throws Exception {
        UUID formatoId  = UUID.randomUUID();
        UUID coordId    = UUID.randomUUID();
        UUID esperado   = UUID.randomUUID();

        when(servicio.evaluarFormato(
                eq(formatoId),
                isNull(),
                eq(coordId),
                eq(EnumDecision.Aprobado),
                eq("Bien hecho")
        )).thenReturn(esperado);

        String out = controller.evaluar(
                formatoId.toString(),
                null,                         // versionId null -> el controller pasa null al servicio
                coordId.toString(),
                "Aprobado",
                "Bien hecho"
        );

        assertEquals("Evaluación registrada con id: " + esperado, out);
        verify(servicio).evaluarFormato(
                eq(formatoId),
                isNull(),
                eq(coordId),
                eq(EnumDecision.Aprobado),
                eq("Bien hecho")
        );
        verifyNoMoreInteractions(servicio);
    }

    @Test
    @DisplayName("OK: evalúa con versionId en blanco ⇒ se pasa null al servicio")
    void evaluar_ok_versionEnBlanco() throws Exception {
        UUID formatoId  = UUID.randomUUID();
        UUID coordId    = UUID.randomUUID();
        UUID esperado   = UUID.randomUUID();

        when(servicio.evaluarFormato(
                eq(formatoId),
                isNull(),
                eq(coordId),
                eq(EnumDecision.Aprobado),
                eq("Bien hecho")
        )).thenReturn(esperado);

        String out = controller.evaluar(
                formatoId.toString(),
                "   ",                        // en blanco -> controller lo convierte a null
                coordId.toString(),
                "Aprobado",
                "Bien hecho"
        );

        assertEquals("Evaluación registrada con id: " + esperado, out);
        verify(servicio).evaluarFormato(
                eq(formatoId),
                isNull(),
                eq(coordId),
                eq(EnumDecision.Aprobado),
                eq("Bien hecho")
        );
        verifyNoMoreInteractions(servicio);
    }

    @Test
    @DisplayName("ERROR: formatoId inválido (UUID mal formado)")
    void evaluar_uuidInvalido() {
        UUID versionId = UUID.randomUUID();
        UUID coordId   = UUID.randomUUID();

        String out = controller.evaluar(
                "no-es-uuid",
                versionId.toString(),
                coordId.toString(),
                "Aprobado",
                "X"
        );

        assertTrue(out.startsWith("Error inesperado:"));
        verifyNoInteractions(servicio);
    }

    @Test
    @DisplayName("ERROR: decisión inválida (valueOf falla)")
    void evaluar_decisionInvalida() {
        UUID formatoId = UUID.randomUUID();
        UUID coordId   = UUID.randomUUID();

        String out = controller.evaluar(
                formatoId.toString(),
                null,
                coordId.toString(),
                "aprobado",   // case incorrecto: EnumDecision.valueOf lanzará IAE
                "X"
        );

        assertTrue(out.startsWith("Error inesperado:"));
        verifyNoInteractions(servicio);
    }

    @Test
    @DisplayName("ERROR: el servicio lanza SQLException")
    void evaluar_sqlException() throws Exception {
        UUID formatoId = UUID.randomUUID();
        UUID coordId   = UUID.randomUUID();

        when(servicio.evaluarFormato(any(), any(), any(), any(), any()))
                .thenThrow(new SQLException("DB error"));

        String out = controller.evaluar(
                formatoId.toString(),
                null,
                coordId.toString(),
                "Aprobado",
                "X"
        );

        assertTrue(out.startsWith("Error inesperado:"));
        verify(servicio).evaluarFormato(any(), any(), any(), any(), any());
        verifyNoMoreInteractions(servicio);
    }
}
