package com.wisechat.main;

import com.wisechat.dao.BusinessDAO;
import com.wisechat.dao.BusinessDAOImpl;
import com.wisechat.dao.UserDAO;
import com.wisechat.dao.UserDAOImpl;
import com.wisechat.model.Business;
import com.wisechat.model.User;
import com.wisechat.util.ConexionDB;

import java.util.List;

/**
 * ============================================================================
 * Clase: App
 * Paquete: com.wisechat.main
 * Proyecto: Wisechat
 * ============================================================================
 *
 * Clase principal de prueba que valida el correcto funcionamiento de los
 * módulos User y Business mediante las implementaciones de los DAOs
 * (UserDAOImpl y BusinessDAOImpl).
 */
public class App {

    public static void main(String[] args) {

        imprimirSeparador("WISECHAT — Prueba CRUD completa: User y Business");

        /*
         * Instanciar los DAOs usando las implementaciones concretas
         */
        UserDAO     userDAO     = new UserDAOImpl();
        BusinessDAO businessDAO = new BusinessDAOImpl();

        int idUsuarioCreado  = -1;
        int idEmpresaCreada  = -1;

        try {

            // =================================================================
            // PASO 1: Crear un nuevo User
            // =================================================================
            imprimirPaso(1, "Crear un nuevo Usuario");

            User nuevoUsuario = new User(
                "Laura Gómez",
                "laura.gomez@wisechat.com",
                "hashed_pass_laurag01"
            );

            idUsuarioCreado = userDAO.insertarUsuario(nuevoUsuario);

            if (idUsuarioCreado == -1) {
                System.err.println("[App] No se pudo crear el usuario. Abortando prueba.");
                return;
            }

            System.out.println("[App] Usuario creado con ID: " + idUsuarioCreado);

            // =================================================================
            // PASO 2: Crear un Business (Negocio) vinculado al User anterior
            // =================================================================
            imprimirPaso(2, "Crear una Empresa vinculada al usuario ID=" + idUsuarioCreado);

            Business nuevaEmpresa = new Business(
                idUsuarioCreado,              
                "Soluciones Wisechat SAS",    
                "Tecnología e Innovación",    
                "Empresa de gestión inteligente."
            );

            businessDAO.insertarNegocio(nuevaEmpresa);

            // =================================================================
            // PASO 3: Listar todos los registros en consola
            // =================================================================
            imprimirPaso(3, "Listar todos los registros en consola");

            System.out.println("  ▶ Lista de Usuarios registrados:");
            List<User> listaUsuarios = userDAO.listarUsuarios();

            if (listaUsuarios.isEmpty()) {
                System.out.println("    (Sin registros en la tabla USER)");
            } else {
                listaUsuarios.forEach(u -> System.out.println("    • " + u));
            }

            System.out.println("\n  ▶ Lista de Negocios (Empresas) registrados:");
            List<Business> listaEmpresas = businessDAO.listarNegocios();

            if (listaEmpresas.isEmpty()) {
                System.out.println("    (Sin registros en la tabla BUSINESS)");
            } else {
                listaEmpresas.forEach(b -> System.out.println("    • " + b));
            }

            if (!listaEmpresas.isEmpty()) {
                idEmpresaCreada = listaEmpresas.get(listaEmpresas.size() - 1).getIdBusiness();
                System.out.println("\n[App] idEmpresaCreada detectado: " + idEmpresaCreada);
            }

            // =================================================================
            // PASO 4: Actualizar el nombre del Business
            // =================================================================
            imprimirPaso(4, "Actualizar el nombre del Negocio ID=" + idEmpresaCreada);

            if (idEmpresaCreada != -1) {
                Business empresaActualizada = new Business(
                    idEmpresaCreada,                       
                    idUsuarioCreado,                       
                    "Wisechat Solutions SAS (Actualizado)", 
                    "Tecnología e Innovación",             
                    "Empresa reestructurada." 
                );

                businessDAO.actualizarNegocio(empresaActualizada);

                System.out.println("[App] Verificación post-actualización:");
                Business verificada = businessDAO.consultarNegocio(idEmpresaCreada);
                if (verificada != null) {
                    System.out.println("    → " + verificada);
                }
            }

            // =================================================================
            // PASO 5: Eliminar los registros de prueba
            // =================================================================
            imprimirPaso(5, "Eliminar registros de prueba");

            if (idEmpresaCreada != -1) {
                System.out.println("  → Eliminando Business ID: " + idEmpresaCreada);
                businessDAO.eliminarNegocio(idEmpresaCreada);
            }

            if (idUsuarioCreado != -1) {
                System.out.println("  → Eliminando User ID: " + idUsuarioCreado);
                userDAO.eliminarUsuario(idUsuarioCreado);
            }

            System.out.println("\n  ▶ Verificación final — Lista de Usuarios:");
            userDAO.listarUsuarios().forEach(u -> System.out.println("    • " + u));

            System.out.println("\n  ▶ Verificación final — Lista de Negocios:");
            businessDAO.listarNegocios().forEach(b -> System.out.println("    • " + b));

        } catch (Exception e) {
            System.err.println("\n[App] ERROR inesperado durante la prueba: " + e.getMessage());
            e.printStackTrace();

        } finally {
            ConexionDB.getInstancia().cerrarConexion();
            imprimirSeparador("Prueba finalizada — Conexión cerrada");
        }
    }

    private static void imprimirSeparador(String titulo) {
        String linea = "═".repeat(60);
        System.out.println("\n" + linea);
        System.out.println("  " + titulo);
        System.out.println(linea + "\n");
    }

    private static void imprimirPaso(int numero, String descripcion) {
        System.out.println("\n┌─────────────────────────────────────────────────────");
        System.out.printf ("│  PASO %d: %s%n", numero, descripcion);
        System.out.println("└─────────────────────────────────────────────────────");
    }
}
