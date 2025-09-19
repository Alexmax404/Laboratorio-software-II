/*
PRUEBAAAAA
PRUEBAAAAA
 */
package co.unicauca.gestiontg.controller;

import co.unicauca.gestiontg.domain.Usuario;

/**
 *
 * @author kthn1
 */
public class SessionManager {

    private static SessionManager instancia;
    private Usuario usuarioActual;

    public SessionManager() {
    }
    
    public static SessionManager getInstancia() {
        if (instancia == null) {
            instancia = new SessionManager();
        }
        return instancia;
    }
    
    public void setUsuarioActual(Usuario user) {
        this.usuarioActual = user;
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public void logout() {
        this.usuarioActual = null;
    }
    
    public boolean isLoggedIn() {
        return usuarioActual != null;
    }
}
