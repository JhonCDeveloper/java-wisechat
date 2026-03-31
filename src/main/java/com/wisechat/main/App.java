package com.wisechat.main;

import com.wisechat.dao.BusinessDAO;
import com.wisechat.dao.UserDAO;
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
 * módulos User y Business mediante sus DAOs con JDBC nativo.
 *
 * Flujo de la prueba:
 *   1. Crear un nuevo User en la base de datos.
 *   2. Crear un Business vinculado al User recién creado.
 *   3. Listar todos los registros de ambas tablas.
 *   4. Actualizar el nombre del Business.
 *   5. Eliminar los registros de prueba (Business primero, luego User).
 *   6. Cerrar la conexión al finalizar.
 *
 * Precondiciones:
 *   - La base de datos 'wisechat_db' debe estar creada y accesible.
 *   - El script database/wisechat_db.sql debe haber sido ejecutado.
 *   - Las credenciales en ConexionDB.java deben estar configuradas.
 *
 * @author  Wisechat - SENA
 * @version 1.0
 */
public class App {

    // =========================================================================
    // MÉTODO PRINCIPAL
    // =========================================================================

    /**
     * Punto de entrada de la aplicación.
     * Ejecuta la prueba CRUD completa para User y Business.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        /* ── Encabezado de la prueba ───────────────────────────────────────── */
        imprimirSeparador("WISECHAT — Prueba CRUD completa: User y Business");

        /*
         * Instanciar los DAOs.
         * Cada DAO obtiene la conexión JDBC a través del Singleton ConexionDB,
         * lo que garantiza que ambos compartan la misma conexión durante la prueba.
         */
        UserDAO     userDAO     = new UserDAO();
        BusinessDAO businessDAO = new BusinessDAO();

        /*
         * IDs de los registros creados durante la prueba.
         * Se inicializan en -1 para detectar si la inserción falló antes de
         * intentar operaciones que dependen de ellos.
         */
        int idUsuarioCreado  = -1;
        int idEmpresaCreada  = -1;

        try {

            // =================================================================
            // PASO 1: Crear un nuevo User
            // =================================================================
            imprimirPaso(1, "Crear un nuevo Usuario");

            /*
             * Se usa el constructor sin ID porque el campo id_user es AUTO_INCREMENT
             * en la base de datos; la BD lo asigna automáticamente.
             */
            User nuevoUsuario = new User(
                "Laura Gómez",                // nombre completo
                "laura.gomez@wisechat.com",   // email único
                "hashed_pass_laurag01"        // contraseña (debe venir hasheada)
            );

            /*
             * insertarUsuario() retorna el id_user generado por AUTO_INCREMENT.
             * Se captura en 'idUsuarioCreado' para usarlo en los pasos siguientes.
             */
            idUsuarioCreado = userDAO.insertarUsuario(nuevoUsuario);

            /* Validar que la inserción haya sido exitosa antes de continuar */
            if (idUsuarioCreado == -1) {
                System.err.println("[App] No se pudo crear el usuario. Abortando prueba.");
                return; // Terminar la prueba si no hay ID válido
            }

            System.out.println("[App] Usuario creado con ID: " + idUsuarioCreado);

            // =================================================================
            // PASO 2: Crear un Business vinculado al User anterior
            // =================================================================
            imprimirPaso(2, "Crear una Empresa vinculada al usuario ID=" + idUsuarioCreado);

            /*
             * Se usa el constructor sin idBusiness porque también es AUTO_INCREMENT.
             * El campo idUser se enlaza con el ID obtenido en el Paso 1,
             * respetando la FK BUSINESS.id_user → USER.id_user.
             */
            Business nuevaEmpresa = new Business(
                idUsuarioCreado,              // FK → id del usuario recién creado
                "Soluciones Wisechat SAS",    // nombre de la empresa
                "Tecnología e Innovación",    // industria o sector
                "Empresa de gestión inteligente de conversaciones con IA."
            );

            /*
             * insertarEmpresa() no retorna el ID generado en la implementación
             * actual (retorna void). Para recuperar el idBusiness, consultamos
             * la lista completa y tomamos el último elemento.
             *
             * NOTA: En proyectos de producción se recomienda usar RETURN_GENERATED_KEYS
             * también en BusinessDAO, tal como se hizo con UserDAO.
             */
            businessDAO.insertarEmpresa(nuevaEmpresa);

            // =================================================================
            // PASO 3: Listar todos los registros en consola
            // =================================================================
            imprimirPaso(3, "Listar todos los registros en consola");

            /* ── Listar usuarios ──────────────────────────────────────────── */
            System.out.println("  ▶ Lista de Usuarios registrados:");
            List<User> listaUsuarios = userDAO.listarTodosLosUsuarios();

            if (listaUsuarios.isEmpty()) {
                System.out.println("    (Sin registros en la tabla USER)");
            } else {
                listaUsuarios.forEach(u -> System.out.println("    • " + u));
            }

            /* ── Listar empresas ──────────────────────────────────────────── */
            System.out.println("\n  ▶ Lista de Empresas registradas:");
            List<Business> listaEmpresas = businessDAO.listarTodasLasEmpresas();

            if (listaEmpresas.isEmpty()) {
                System.out.println("    (Sin registros en la tabla BUSINESS)");
            } else {
                listaEmpresas.forEach(b -> System.out.println("    • " + b));
            }

            /*
             * Recuperar la empresa recién creada para obtener su ID auto-generado.
             * Se toma el último elemento de la lista (el más recientemente insertado).
             */
            if (!listaEmpresas.isEmpty()) {
                idEmpresaCreada = listaEmpresas.get(listaEmpresas.size() - 1).getIdBusiness();
                System.out.println("\n[App] idEmpresaCreada detectado: " + idEmpresaCreada);
            }

            // =================================================================
            // PASO 4: Actualizar el nombre del Business
            // =================================================================
            imprimirPaso(4, "Actualizar el nombre de la Empresa ID=" + idEmpresaCreada);

            if (idEmpresaCreada != -1) {
                /*
                 * Construir el objeto Business con el mismo idBusiness  e idUser,
                 * pero con el nombre y la descripción modificados.
                 */
                Business empresaActualizada = new Business(
                    idEmpresaCreada,                       // PK identificador
                    idUsuarioCreado,                       // FK al usuario propietario
                    "Wisechat Solutions SAS (Actualizado)", // nuevo nombre
                    "Tecnología e Innovación",             // industria sin cambio
                    "Empresa reestructurada y rebranding completado." // nueva desc.
                );

                /* Ejecutar la actualización en la BD */
                businessDAO.actualizarEmpresa(empresaActualizada);

                /* Verificar el cambio consultando el registro actualizado */
                System.out.println("[App] Verificación post-actualización:");
                Business verificada = businessDAO.consultarEmpresaPorId(idEmpresaCreada);
                if (verificada != null) {
                    System.out.println("    → " + verificada);
                }
            } else {
                System.out.println("[App] No se pudo actualizar: idEmpresaCreada es -1.");
            }

            // =================================================================
            // PASO 5: Eliminar los registros de prueba
            // =================================================================
            imprimirPaso(5, "Eliminar registros de prueba");

            /*
             * ORDEN DE ELIMINACIÓN IMPORTANTE:
             * Se debe eliminar primero el Business antes que el User para respetar
             * la restricción de clave foránea (FK):
             *   BUSINESS.id_user → USER.id_user
             *
             * Si se elimina el User primero con ON DELETE CASCADE, la BD lo haría
             * automáticamente; pero en este flujo lo hacemos explícitamente
             * para demostrar el orden correcto.
             */
            if (idEmpresaCreada != -1) {
                System.out.println("  → Eliminando Business ID: " + idEmpresaCreada);
                businessDAO.eliminarEmpresa(idEmpresaCreada);
            }

            if (idUsuarioCreado != -1) {
                System.out.println("  → Eliminando User ID: " + idUsuarioCreado);
                userDAO.eliminarUsuario(idUsuarioCreado);
            }

            /* ── Verificación final: las listas deben estar vacías o sin el registro ── */
            System.out.println("\n  ▶ Verificación final — Lista de Usuarios:");
            userDAO.listarTodosLosUsuarios().forEach(u -> System.out.println("    • " + u));

            System.out.println("\n  ▶ Verificación final — Lista de Empresas:");
            businessDAO.listarTodasLasEmpresas().forEach(b -> System.out.println("    • " + b));

        } catch (Exception e) {
            /*
             * Capturar cualquier excepción no controlada dentro del flujo de prueba.
             * Se imprime el mensaje y el stack trace completo para diagnóstico.
             */
            System.err.println("\n[App] ERROR inesperado durante la prueba: " + e.getMessage());
            e.printStackTrace();

        } finally {
            /*
             * El bloque finally garantiza que la conexión SIEMPRE se cierre,
             * incluso si ocurre una excepción en algún paso anterior.
             * Esto libera los recursos de la BD correctamente.
             */
            ConexionDB.getInstancia().cerrarConexion();
            imprimirSeparador("Prueba finalizada — Conexión cerrada");
        }
    }

    // =========================================================================
    // MÉTODOS AUXILIARES DE PRESENTACIÓN
    // =========================================================================

    /**
     * Imprime un separador visual con un título centrado en la consola.
     *
     * @param titulo Texto a mostrar dentro del separador.
     */
    private static void imprimirSeparador(String titulo) {
        String linea = "═".repeat(60);
        System.out.println("\n" + linea);
        System.out.println("  " + titulo);
        System.out.println(linea + "\n");
    }

    /**
     * Imprime el encabezado de cada paso del flujo de prueba.
     *
     * @param numero Número del paso (1, 2, 3…).
     * @param descripcion Descripción breve de la operación.
     */
    private static void imprimirPaso(int numero, String descripcion) {
        System.out.println("\n┌─────────────────────────────────────────────────────");
        System.out.printf ("│  PASO %d: %s%n", numero, descripcion);
        System.out.println("└─────────────────────────────────────────────────────");
    }
}
