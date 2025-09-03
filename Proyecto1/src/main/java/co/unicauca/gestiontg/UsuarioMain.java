package co.unicauca.gestiontg;

import co.unicauca.gestiontg.access.GestionUsuario;
import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.ui.logInFrame;
import co.unicauca.gestiontg.service.Servicio;

public class UsuarioMain {

    public static void main(String[] args) {
        IUsuarioRepositorio repositorio = GestionUsuario.getInstancia().getRepositorio("SQLite");
        Servicio servicio = new Servicio(repositorio);
        logInFrame log = new logInFrame(servicio);
        
        servicio.addObserver(log); 
        
        log.setVisible(true);
        //Usuarios de prueba
        //Estudiante juanPerez@unicauca.edu.co contra: Juan020903.
        //Docente AngelaPerez@unicauca.edu.co contra : ANGELA1234!
        //prueba2@unicauca.edu.co P1234.
        //prueba3@unicauca.edu.co P1234.
    }
    
}
