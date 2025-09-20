package co.unicauca.gestiontg;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.access.UsuarioRepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.EnumPrograma;
import co.unicauca.gestiontg.domain.EnumRol;
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.service.ServicioFormatoA;
import co.unicauca.gestiontg.service.ServicioUsuario;
import java.util.UUID;

/**
 *
 * @author kthn1
 */
public class AppPruebas {

    public static void main(String[] args) {

        var repo = new FormatoARepositorio();
        var servicio = new ServicioFormatoA(repo);

        var controller = new FormatoAController(servicio);
        
        UUID estudianteId1 = UUID.fromString("65bfdcfb-cdd9-4bb8-951a-4a5ff64f709b");
        UUID estudianteId2 = UUID.fromString("87c4ab0d-21d5-4f08-941c-9bc3af22d434");
        UUID docenteId = UUID.fromString("bfa36952-998e-40ba-803b-749dac59c31d");
        UUID enviadoPor = docenteId;

//        // Primer envío (creación)
//        String result1 = controller.crearOReenviarFormato(
//                null,
//                estudianteId1.toString(),
//                null,
//                docenteId.toString(),
//                enviadoPor.toString(),
//                "Sistema de Gestión de TG",
//                "TRABAJO_DE_INVESTIGACION",
//                "Dr. López",
//                null,
//                "2025-09-20",
//                "Objetivo general del proyecto",
//                "Objetivo específico 1; Objetivo 2",
//                null,
//                "/uploads/formatoA_v1.pdf"
//        );
//        System.out.println("Resultado primer envío: " + result1);

        // Reenvío (corrección) - Extraer formatoId de result1 para pruebas reales
        // Ejemplo: supongamos que el formatoId es "33333333-3333-3333-3333-333333333333"
        String formatoId = "cc4d0108-d8c8-4aae-a6dc-a4c58473553d"; 
        String result2 = controller.crearOReenviarFormato(
            formatoId,
            estudianteId1.toString(),
            estudianteId2.toString(),
            docenteId.toString(),
            enviadoPor.toString(),
            "Sistema de Gestión de TG - Corregido3",
            "TRABAJO_DE_INVESTIGACION",
            "Dr. López",
            null,
            "2025-09-25",
            "Objetivo general actualizado",
            "Objetivo específico 1 actualizado",
            null,
            "/uploads/formatoA_v2.pdf"
        );
        System.out.println("Resultado reenvío: " + result2);
    }
}