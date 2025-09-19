package co.unicauca.gestiontg;

import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.service.ServicioFormatoA;
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

        UUID estudianteId1 = UUID.fromString("c411a4ad-f57f-4976-846d-df75da97d812");
        UUID estudianteId2 = UUID.fromString("b40c8c09-342a-425a-9e35-e47bdebc298e");
        UUID enviadoPor = estudianteId1;
        
        // Primer envío (creación)
//        String result1 = controller.crearOReenviarFormato(
//            null,
//            estudianteId1.toString(),
//            estudianteId2.toString(),
//            enviadoPor.toString(),
//            "Sistema de Gestión de TG",
//            "TRABAJO_DE_INVESTIGACION",
//            "Dr. López",
//            null,
//            "2025-09-20",
//            "Objetivo general del proyecto",
//            "Objetivo específico 1; Objetivo 2",
//            null,
//            "/uploads/formatoA_v1.pdf"
//        );
//        System.out.println("Resultado primer envío: " + result1);

        // Reenvío (corrección) - Extraer formatoId de result1 para pruebas reales
        // Ejemplo: supongamos que el formatoId es "33333333-3333-3333-3333-333333333333"
        String formatoId = "c330fcf7-4ef6-4c55-8f93-9df38eee2b1b"; // Reemplazar con ID real
        String result2 = controller.crearOReenviarFormato(
            formatoId,
            estudianteId1.toString(),
            estudianteId2.toString(),
            enviadoPor.toString(),
            "Sistema de Gestión de TG - Corregido",
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
