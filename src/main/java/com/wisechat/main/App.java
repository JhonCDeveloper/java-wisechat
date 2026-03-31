package com.wisechat.main;

import com.wisechat.dao.BusinessDAO;
import com.wisechat.dao.UserDAO;
import com.wisechat.model.Business;
import com.wisechat.model.User;
import com.wisechat.util.ConexionDB;

import java.util.List;

/**
 * Clase de prueba principal — Módulos User y Business.
 * Verifica las operaciones CRUD mediante los DAOs con JDBC.
 */
public class App {

    public static void main(String[] args) {

        System.out.println("════════════════════════════════════════");
        System.out.println("  WISECHAT — Prueba CRUD User & Business");
        System.out.println("════════════════════════════════════════\n");

        try {
            // ── Instanciar DAOs ──────────────────────────────────────────────
            UserDAO     userDAO     = new UserDAO();
            BusinessDAO businessDAO = new BusinessDAO();

            // ════════════════════════════════════════════════════════════════
            // BLOQUE 1: CRUD de USER
            // ════════════════════════════════════════════════════════════════
            System.out.println("─── [USER] Insertar ───────────────────────");
            User nuevoUsuario = new User("Carlos Mendoza", "carlos@wisechat.com", "pass_hasheada_123");
            userDAO.insertarUsuario(nuevoUsuario);

            System.out.println("\n─── [USER] Listar todos ───────────────────");
            List<User> usuarios = userDAO.listarTodosLosUsuarios();
            usuarios.forEach(System.out::println);

            System.out.println("\n─── [USER] Consultar por ID (id=1) ────────");
            User usuarioEncontrado = userDAO.consultarUsuarioPorId(1);
            if (usuarioEncontrado != null) {
                System.out.println("Encontrado: " + usuarioEncontrado);
            }

            System.out.println("\n─── [USER] Actualizar (id=1) ──────────────");
            User usuarioActualizado = new User(1, "Carlos Mendoza (Act.)", "carlos.nuevo@wisechat.com", "nueva_pass_hasheada", null);
            userDAO.actualizarUsuario(usuarioActualizado);

            System.out.println("\n─── [USER] Eliminar (id=1) ────────────────");
            userDAO.eliminarUsuario(1);

            // ════════════════════════════════════════════════════════════════
            // BLOQUE 2: CRUD de BUSINESS
            // ════════════════════════════════════════════════════════════════
            System.out.println("\n─── [BUSINESS] Insertar ───────────────────");
            Business nuevaEmpresa = new Business(2, "InnovateTech SAS", "Tecnología", "Empresa líder en IA.");
            businessDAO.insertarEmpresa(nuevaEmpresa);

            System.out.println("\n─── [BUSINESS] Listar todas ───────────────");
            List<Business> empresas = businessDAO.listarTodasLasEmpresas();
            empresas.forEach(System.out::println);

            System.out.println("\n─── [BUSINESS] Consultar por ID (id=1) ────");
            Business empresaEncontrada = businessDAO.consultarEmpresaPorId(1);
            if (empresaEncontrada != null) {
                System.out.println("Encontrada: " + empresaEncontrada);
            }

            System.out.println("\n─── [BUSINESS] Actualizar (id=1) ──────────");
            Business empresaActualizada = new Business(1, 2, "InnovateTech SAS (Act.)", "IA y Datos", "Descripción actualizada.");
            businessDAO.actualizarEmpresa(empresaActualizada);

            System.out.println("\n─── [BUSINESS] Eliminar (id=1) ────────────");
            businessDAO.eliminarEmpresa(1);

        } catch (Exception e) {
            System.err.println("\n[ERROR GENERAL] " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar la conexión al finalizar
            ConexionDB.getInstancia().cerrarConexion();
            System.out.println("\n════════════════════════════════════════");
            System.out.println("  Pruebas finalizadas.");
            System.out.println("════════════════════════════════════════");
        }
    }
}
