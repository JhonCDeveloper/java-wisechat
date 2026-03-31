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
    private Connection conexion;

    /**
     * Constructor privado: carga el driver y establece la conexión.
     */
    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("[ConexionDB] Conexión establecida con éxito.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("[ConexionDB] Driver MySQL no encontrado: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("[ConexionDB] Error al conectar con la BD: " + e.getMessage(), e);
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
     * Retorna el objeto Connection activo.
     *
     * @return objeto java.sql.Connection
     */
    public Connection getConexion() {
        try {
            // Re-conectar si la conexión fue cerrada o perdida
            if (conexion == null || conexion.isClosed()) {
                instancia = new ConexionDB();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ConexionDB] Error al verificar la conexión: " + e.getMessage(), e);
        }
        return conexion;
    }

    /**
     * Cierra la conexión activa y destruye la instancia singleton.
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                instancia = null;
                System.out.println("[ConexionDB] Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("[ConexionDB] Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
