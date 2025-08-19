package co.unicauca.gestiontg;

import co.unicauca.gestiontg.service.Servicio;

public class UsuarioMain {

    public static void main(String[] args) {
        Servicio servicio = new Servicio(null);

        // Probar correos
        System.out.println(servicio.validarCorreoInstitucional("pepito@unicauca.edu.co")); 
        System.out.println(servicio.validarCorreoInstitucional("pepito@gmail.com")); 
        // Probar contraseñas
        System.out.println(servicio.validarContrasenia("Pepito123@")); 
        System.out.println(servicio.validarContrasenia("pepit")); 
        System.out.println(servicio.validarContrasenia("pepito")); 
        System.out.println(servicio.validarContrasenia("Pepito123")); 
        System.out.println(servicio.validarContrasenia("P@ssword")); 
    }
    
}
