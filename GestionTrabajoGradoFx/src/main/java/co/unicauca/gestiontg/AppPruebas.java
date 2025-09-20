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
//
        UUID estudianteId1 = UUID.fromString("4c3fdc7c-b8fc-4e3f-91fb-05e495d75b5b");
        UUID estudianteId2 = UUID.fromString("7b45eed0-65be-45c1-b311-691f84bc6a34");
        UUID enviadoPor = estudianteId1;

        // Primer envío (creación)
        String result1 = controller.crearOReenviarFormato(
                null,
                estudianteId1.toString(),
                estudianteId2.toString(),
                enviadoPor.toString(),
                "Sistema de Gestión de TG",
                "TRABAJO_DE_INVESTIGACION",
                "Dr. López",
                null,
                "2025-09-20",
                "Objetivo general del proyecto",
                "Objetivo específico 1; Objetivo 2",
                null,
                "/uploads/formatoA_v1.pdf"
        );
        System.out.println("Resultado primer envío: " + result1);

//        // Reenvío (corrección) - Extraer formatoId de result1 para pruebas reales
//        // Ejemplo: supongamos que el formatoId es "33333333-3333-3333-3333-333333333333"
//        String formatoId = "bb1c3bee-f537-43bf-b80f-6a4e533ae751"; // Reemplazar con ID real
//        String result2 = controller.crearOReenviarFormato(
//            formatoId,
//            estudianteId1.toString(),
//            estudianteId2.toString(),
//            enviadoPor.toString(),
//            "Sistema de Gestión de TG - Corregido",
//            "TRABAJO_DE_INVESTIGACION",
//            "Dr. López",
//            null,
//            "2025-09-25",
//            "Objetivo general actualizado",
//            "Objetivo específico 1 actualizado",
//            null,
//            "/uploads/formatoA_v2.pdf"
//        );
//        System.out.println("Resultado reenvío: " + result2);
    }
}