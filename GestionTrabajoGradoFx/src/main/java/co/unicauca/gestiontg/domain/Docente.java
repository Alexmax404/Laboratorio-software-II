
package co.unicauca.gestiontg.domain;


public class Docente extends Usuario{
    
    public Docente(String nombres, String apellidos, String celular, EnumPrograma programa, EnumRol rol, String correo, String contrasenia) {
        super(nombres, apellidos, celular, programa, rol, correo, contrasenia);
    }
    
}
