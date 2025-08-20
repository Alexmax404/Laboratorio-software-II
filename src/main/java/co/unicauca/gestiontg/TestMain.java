package co.unicauca.gestiontg;
import co.unicauca.gestiontg.access.UsuarioRepositorio;
import co.unicauca.gestiontg.domain.*;
import co.unicauca.gestintg.ui.*;
public class TestMain {
    
    public static void main(String[] args) {
        logInFrame login = new logInFrame();
        registerFrame register = new registerFrame();
        
        
        
        // Centrar en pantalla
        login.setLocationRelativeTo(null);

        // Mostrar ventana
        login.setVisible(true);
        UsuarioRepositorio repo = new UsuarioRepositorio();

        //prueba de inicio de sesion
        
        String correo = login.getTfCorreoElectronico().getText();
        String contraseña = login.getPwtfContraseña().getText();
        
        // Crear usuario
        Usuario u = new Usuario("Juan", "Pérez", "12345678", 
                                EnumPrograma.IngenieriaDeSistemas, 
                                EnumRol.Estudiante, 
                                "juan.perez@unicauca.edu.co", 
                                "12345"); //contrasenia original 12345

         //Registrar usuario
//        boolean registrado = repo.registrarUsuario(u)  ;
//        System.out.println("¿Usuario registrado?: " + registrado);

//      Intentar iniciar sesión con la contrasenia correcta

//        boolean loginOK = repo.iniciarSesion(u);
//        System.out.println("¿Login correcto?: " + loginOK);
//
//        // Intentar iniciar sesión con la contraseña incorrecta
//
//        u.setContrasenia("12345");
//        boolean loginFail = repo.iniciarSesion(u);
//        System.out.println("¿Login correcto?: " + loginFail);
    }
}