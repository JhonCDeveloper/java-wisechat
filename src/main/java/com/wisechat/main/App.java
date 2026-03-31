package com.wisechat.main;

import com.wisechat.dao.*;
import com.wisechat.model.*;
import com.wisechat.util.ConexionDB;

/**
 * Clase principal de prueba del proyecto Wisechat.
 * Verifica la conexión y ejecuta operaciones CRUD básicas.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   WISECHAT - Prueba de Conexión   ");
        System.out.println("====================================\n");

        try {
            // --- 1. Verificar conexión (Singleton) ---
            ConexionDB db = ConexionDB.getInstancia();
            System.out.println("Conexión obtenida: " + db.getConexion());

            // --- 2. Prueba: USER ---
            System.out.println("\n[TEST] Creando usuario...");
            UserDAO userDAO = new UserDAOImpl();
            User nuevoUsuario = new User();
            nuevoUsuario.setName("Ana López");
            nuevoUsuario.setEmail("ana.lopez@wisechat.com");
            nuevoUsuario.setPassword("hashed_password_123");
            userDAO.crear(nuevoUsuario);

            System.out.println("[TEST] Listando usuarios:");
            userDAO.listarTodo().forEach(System.out::println);

            // --- 3. Prueba: BUSINESS ---
            System.out.println("\n[TEST] Creando empresa...");
            BusinessDAO businessDAO = new BusinessDAOImpl();
            Business empresa = new Business();
            empresa.setIdUser(1);
            empresa.setName("Tech Solutions SAS");
            empresa.setIndustry("Tecnología");
            empresa.setDescription("Empresa de desarrollo de software.");
            businessDAO.crear(empresa);

            System.out.println("[TEST] Listando empresas:");
            businessDAO.listarTodo().forEach(System.out::println);

            // --- 4. Prueba: MESSAGES ---
            System.out.println("\n[TEST] Registrando mensaje...");
            MessageDAO messageDAO = new MessageDAOImpl();
            Message msg = new Message();
            msg.setIdUser(1);
            msg.setMessageText("¡Hola desde Wisechat!");
            msg.setSender("user");
            messageDAO.crear(msg);

            System.out.println("[TEST] Listando mensajes:");
            messageDAO.listarTodo().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("\n[ERROR] " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar la conexión al terminar
            ConexionDB.getInstancia().cerrarConexion();
            System.out.println("\n====================================");
            System.out.println("   Pruebas finalizadas.            ");
            System.out.println("====================================");
        }
    }
}
