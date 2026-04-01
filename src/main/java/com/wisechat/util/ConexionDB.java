package com.wisechat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión a la base de datos.
 * Implementa el patrón Singleton para garantizar una única instancia de conexión.
 */
public class ConexionDB {

    // --- Configuración de la base de datos ---
    private static final String URL      = "jdbc:mysql://localhost:3306/wisechat_db";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = ""; // Cambiar según configuración local

    // --- Instancia única (Singleton) ---
    private static ConexionDB instancia = null;

    /**
     * Constructor privado: carga el driver de la base de datos.
     */
    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("[ConexionDB] Driver MySQL no encontrado: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna la única instancia de ConexionDB (thread-safe).
     *
     * @return instancia singleton de ConexionDB
     */
    public static synchronized ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    /**
     * Retorna un nuevo objeto Connection activo cada vez que se llama.
     * Al usar try-with-resources en los DAOs, esta conexión se cerrará automáticamente de manera segura.
     *
     * @return objeto java.sql.Connection
     */
    public Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("[ConexionDB] Error al obtener nueva conexión: " + e.getMessage(), e);
        }
    }

    /**
     * Cierra la instancia singleton. En este patrón las conexiones individuales
     * deben ser cerradas localmente (ej: try-with-resources).
     */
    public void cerrarConexion() {
        instancia = null;
    }
}
