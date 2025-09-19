package co.unicauca.gestiontg.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author kthn1
 */
public class DatabaseConfig {

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            // Primero intento variables de entorno
            url = System.getenv("DB_URL");
            user = System.getenv("DB_USER");
            password = System.getenv("DB_PASSWORD");

            // Si no existen, leo application.properties
            if (url == null || user == null || password == null) {
                try (InputStream input = DatabaseConfig.class
                        .getClassLoader()
                        .getResourceAsStream("application.properties")) {

                    Properties prop = new Properties();
                    prop.load(input);
                    url = prop.getProperty("DB_URL");
                    user = prop.getProperty("DB_USER");
                    password = prop.getProperty("DB_PASSWORD");
                }
            }

            // Registrar el driver explícitamente
            Class.forName("org.postgresql.Driver");

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error cargando configuración de BD", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
