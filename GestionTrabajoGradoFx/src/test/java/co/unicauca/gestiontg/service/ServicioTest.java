/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package co.unicauca.gestiontg.service;

import co.unicauca.gestiontg.access.IUsuarioRepositorio;
import co.unicauca.gestiontg.access.UsuarioRepositorio;
import co.unicauca.gestiontg.domain.EnumPrograma;
import co.unicauca.gestiontg.domain.EnumRol;
import co.unicauca.gestiontg.domain.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author angma
 */
public class ServicioTest {
    public ServicioTest() {
    }


//    @org.junit.jupiter.api.Test
//    public void testValidarContrasenia1() {
//        System.out.println("validarContraseniaCorrecta");
//        String contrasenia = "Atenea%123";
//        IUsuarioRepositorio Repositorio = new  UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);
//        String expResult = "OK";
//        String result = instance.validarContrasenia(contrasenia);
//        assertEquals(expResult, result);
//       // fail("The test case is a prototype.");
//    }
//    @org.junit.jupiter.api.Test
//    public void testValidarContrasenia2() {
//        System.out.println("validarContraseniaMayuscula");
//        String contrasenia = "atenea%123";
//        IUsuarioRepositorio Repositorio = new  UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);
//        String expResult = "La contraseña debe contener al menos una letra mayuscula\n";
//        String result = instance.validarContrasenia(contrasenia);
//        assertEquals(expResult, result);
//       // fail("The test case is a prototype.");
//    }
//    @org.junit.jupiter.api.Test
//    public void testValidarContrasenia3() {
//        System.out.println("validarContraseniaNumber");
//        String contrasenia = "Atenea%";
//        IUsuarioRepositorio Repositorio = new  UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);
//        String expResult = "La contraseña debe contener al menos un numero\n";
//        String result = instance.validarContrasenia(contrasenia);
//        assertEquals(expResult, result);
//       // fail("The test case is a prototype.");
//    }
//    @org.junit.jupiter.api.Test
//    public void testValidarContrasenia4() {
//        System.out.println("validarContrasenia Especial");
//        String contrasenia = "Atenea123";
//        IUsuarioRepositorio Repositorio = new  UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);
//        String expResult = "La contraseña debe contener al menos un caracter especial\n";
//        String result = instance.validarContrasenia(contrasenia);
//        assertEquals(expResult, result);
//    }
//    
//      @org.junit.jupiter.api.Test
//    public void testInicioSesion() {
//        System.out.println("inicioSesionInvalido");
//        String correo = "juancamilo@eminencia.com";
//        String contrasenia = "12345";
//        IUsuarioRepositorio Repositorio = new  UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);;
//        boolean expResult = false;
//        boolean result = instance.inicioSesion(correo, contrasenia);
//        assertEquals(expResult, result);
//       // fail("The test case is a prototype.");
//    }
//    @org.junit.jupiter.api.Test
//    public void testInicioSesion2() {
//        System.out.println("inicioSesionInvalidoContrasenia");
//        String correo = "juan.perez@unicauca.edu.co";
//        String contrasenia = "woyegfihwvefh";
//        IUsuarioRepositorio Repositorio = new  UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);;
//        boolean expResult = false;
//        boolean result = instance.inicioSesion(correo, contrasenia);
//        assertEquals(expResult, result);
//    }
//    @org.junit.jupiter.api.Test
//    public void testInicioSesion3() {
//        System.out.println("inicioSesionValido");
//        String correo = "LuFerpas@unicauca.edu.co";
//        String contrasenia = "Nico020903.";
//        IUsuarioRepositorio Repositorio = new  UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);
//        boolean expResult = true;
//        boolean result = instance.inicioSesion(correo, contrasenia);
//        assertEquals(expResult, result);
//    }
//    
//    @Test
//    void testRegistrarUsuarioConMock() throws Exception {
//        System.out.println("Registrar usuario con Mockito");
//        IUsuarioRepositorio mockRepositorio = mock(IUsuarioRepositorio.class);
//        Servicio servicio = new Servicio(mockRepositorio);
//        Usuario newUsuario = new Usuario("Juan", "Perez", "21516",EnumPrograma.IngenieriaDeSistemas, EnumRol.Docente,"mockuser@unicauca.edu.co", "AA11ac%$#$123");
//        when(mockRepositorio.registrarUsuario(newUsuario)).thenReturn(true);
//        boolean result = servicio.registrarUsuario(newUsuario);
//        assertTrue(result);
//        verify(mockRepositorio, times(1)).registrarUsuario(newUsuario);
//    } 
//     
//     @org.junit.jupiter.api.Test
//      public void testRegistrarUsuario2() throws Exception {
//        System.out.println("registrarUsuarioNuevoUnicauca");        
//        Usuario newUsuario= new Usuario("Juan", "Zuluaga", "21516", EnumPrograma.IngenieriaDeSistemas, EnumRol.Docente, "juan@unicauca.edu", "AA11ac%$#$123");
//        IUsuarioRepositorio Repositorio = new UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);
//        boolean expResult = false;
//        boolean result = instance.registrarUsuario(newUsuario);
//        assertEquals(expResult, result);
//    }
//      @org.junit.jupiter.api.Test
//      public void testRegistrarUsuario3() throws Exception {
//        System.out.println("registrarUsuarioNuevoContrasenia");        
//        Usuario newUsuario= new Usuario("Juan", "Zuluaga", "21516", EnumPrograma.IngenieriaDeSistemas, EnumRol.Docente, "juan@unicauca.edu.co", "");
//        IUsuarioRepositorio Repositorio = new UsuarioRepositorio();
//        Servicio instance = new Servicio(Repositorio);
//        boolean expResult = false;
//        boolean result = instance.registrarUsuario(newUsuario);
//        assertEquals(expResult, result);
//    } 
//      @org.junit.jupiter.api.Test
//      public void testObtenerRolUsuario() {
//        System.out.println("Obtener rol de usuario existente.");
//        String email = "LuFerpas@unicauca.edu.co";
//        IUsuarioRepositorio repository = new UsuarioRepositorio();
//        Servicio instance = new Servicio(repository);        
//        String expResult = "Estudiante";
//        String result = instance.obtenerRolUsuario(email);
//        assertEquals(expResult, result);
//    }
      

     
      
    
    
    


  
   
    
    
}