package co.unicauca.gestiontg;

import co.unicauca.gestiontg.access.EvaluacionCoordinadorRepositorio;
import co.unicauca.gestiontg.access.FormatoARepositorio;
import co.unicauca.gestiontg.access.IEvaluacionCoordinadorRepositorio;
import co.unicauca.gestiontg.access.IFormatoARepositorio;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.access.UsuarioRepositorio;
import co.unicauca.gestiontg.controller.AuthController;
import co.unicauca.gestiontg.controller.EvaluacionCoordinadorController;
import co.unicauca.gestiontg.controller.FormatoAController;
import co.unicauca.gestiontg.domain.EnumPrograma;
import co.unicauca.gestiontg.domain.EnumRol;
import co.unicauca.gestiontg.domain.Usuario;
import co.unicauca.gestiontg.service.ServicioEvaluacionCoordinador;
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
        
        UUID estudianteId1 = UUID.fromString("33d36fc0-7518-4eb7-bca1-956e1cbfc021");
        UUID estudianteId2 = UUID.fromString("87c4ab0d-21d5-4f08-941c-9bc3af22d434");
        UUID docenteId = UUID.fromString("8af4eb50-9c37-4b97-8de2-a315f896b589");
        UUID enviadoPor = docenteId;
        // Primer envío (creación)
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
        String formatoId = "6bd90dcc-993b-4285-8e5f-0b3d2b86d695"; 
        String result2 = controller.crearOReenviarFormato(
            formatoId,
            estudianteId1.toString(),
            estudianteId2.toString(),
            docenteId.toString(),
            enviadoPor.toString(),
            "Sistema de Gestión de TG - Corregido2",
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

//        IFormatoARepositorio formatoRepo = new FormatoARepositorio();
//        IEvaluacionCoordinadorRepositorio evalRepo = new EvaluacionCoordinadorRepositorio();
//
//        ServicioEvaluacionCoordinador servicio = new ServicioEvaluacionCoordinador(evalRepo, formatoRepo);
//        EvaluacionCoordinadorController controller = new EvaluacionCoordinadorController(servicio);
//
//        // Parámetros de ejemplo (ajusta IDs de acuerdo a tu BD)
//        String formatoId = "6bd90dcc-993b-4285-8e5f-0b3d2b86d695";
//        String formatoVersionId = "f57ffcf3-5871-4f36-8e73-3a8253b7f2e3";
//        String coordinadorId = "3295aec4-1ed7-4d48-bd61-f74f305a308b";
//        String decision = "Aprobado"; // Aprobado | Correcciones | Rechazado
//        String comentarios = "Por favor ajustar título";
//
//        String resultado = controller.evaluar(formatoId, formatoVersionId, coordinadorId, decision, comentarios);
//        System.out.println(resultado);
    }
}
