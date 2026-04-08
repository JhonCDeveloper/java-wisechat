package com.wisechat.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wisechat.dao.UserDAO;
import com.wisechat.dao.UserDAOImpl;
import com.wisechat.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * <b>REST Controller — Registro de Usuarios (HU-03: Registrarme)</b>
 *
 * <p>Expone el endpoint {@code POST /api/register} que recibe los datos del
 * formulario de Registro de React ({@code Register.jsx}) y crea un nuevo
 * usuario en la tabla {@code USER} de la base de datos mediante JDBC.</p>
 *
 * <p>Flujo de la Historia de Usuario HU-03:</p>
 * <ol>
 *   <li>El frontend envía {@code { "name", "email", "password" }} en JSON.</li>
 *   <li>Se verifica que el correo no esté ya registrado en la base de datos.</li>
 *   <li>Si el correo es nuevo, se crea el usuario con {@code UserDAO.insertarUsuario()}.</li>
 *   <li>Se responde con {@code 201 Created} y el {@code userId} generado,
 *       lo cual permite al frontend redirigir directo al onboarding (HU-04).</li>
 *   <li>Si el correo ya existe, se responde con {@code 409 Conflict}.</li>
 * </ol>
 *
 * <p><b>Payload de entrada esperado:</b></p>
 * <pre>
 * {
 *   "name":     "Jhon",
 *   "email":    "jhon@ejemplo.com",
 *   "password": "contraseña123"
 * }
 * </pre>
 *
 * <p><b>Respuesta exitosa:</b></p>
 * <pre>
 * {
 *   "success": true,
 *   "message": "Registro exitoso.",
 *   "userId":  5
 * }
 * </pre>
 *
 * @author  Wisechat Team
 * @version 2.0 — Evidencia AA3-EV01
 * @see     UserDAO
 * @see     LoginRestController
 */
@WebServlet(name = "RegisterRestController", urlPatterns = {"/api/register"})
public class RegisterRestController extends HttpServlet {

    /** Interfaz de acceso a datos para operaciones CRUD sobre la tabla USER. */
    private UserDAO userDAO;

    /** Instancia compartida de Gson para parseo de JSON. */
    private Gson gson;

    // ─── Inicialización del Servlet ─────────────────────────────────────────

    /**
     * Inicializa las dependencias del controlador al desplegar el WAR en Tomcat.
     *
     * @throws ServletException si la inicialización falla.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        this.userDAO = new UserDAOImpl();
        this.gson    = new Gson();
    }

    // ─── Handler POST /api/register ──────────────────────────────────────────

    /**
     * Maneja la petición {@code POST /api/register} enviada por el componente
     * {@code Register.jsx} del frontend React.
     *
     * <p>Realiza las siguientes validaciones antes de persistir:</p>
     * <ul>
     *   <li>Que el cuerpo JSON no sea nulo.</li>
     *   <li>Que {@code email} y {@code password} estén presentes.</li>
     *   <li>Que el correo no esté ya registrado en la tabla USER.</li>
     * </ul>
     *
     * <p>Si no se envía {@code name}, se asigna el valor por defecto "Usuario"
     * para no bloquear el registro (el nombre es {@code NOT NULL} en DB).</p>
     *
     * @param request  Petición HTTP con cuerpo JSON {@code { name, email, password }}.
     * @param response Respuesta HTTP en formato JSON.
     * @throws ServletException si ocurre un error en el proceso del Servlet.
     * @throws IOException      si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Establecer cabeceras de respuesta JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Deserializar el cuerpo JSON de la petición
            BufferedReader reader       = request.getReader();
            RegisterRequest regRequest  = gson.fromJson(reader, RegisterRequest.class);

            // Validar presencia de campos obligatorios
            if (regRequest == null
                    || regRequest.email    == null
                    || regRequest.password == null) {
                enviarRespuesta(response, HttpServletResponse.SC_BAD_REQUEST,
                        false, "Correo y contraseña son requeridos.", -1);
                return;
            }

            // Verificar duplicado: el correo debe ser único en la tabla USER
            User usuarioExistente = userDAO.buscarPorEmail(regRequest.email.trim());
            if (usuarioExistente != null) {
                enviarRespuesta(response, HttpServletResponse.SC_CONFLICT,
                        false, "Este correo ya está registrado.", -1);
                return;
            }

            // Asignar nombre por defecto si el frontend no lo envía (gap de HU-03)
            String nombre = (regRequest.name != null && !regRequest.name.isBlank())
                    ? regRequest.name.trim()
                    : "Usuario";

            // Crear la entidad User y persistir en la base de datos
            User nuevoUsuario = new User(nombre, regRequest.email.trim(), regRequest.password.trim());
            int  idGenerado   = userDAO.insertarUsuario(nuevoUsuario);

            if (idGenerado > 0) {
                // Registro exitoso: devolver userId para que React inicie el onboarding (HU-04)
                enviarRespuesta(response, HttpServletResponse.SC_CREATED,
                        true, "Registro exitoso.", idGenerado);
            } else {
                enviarRespuesta(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        false, "Error al guardar el usuario en la base de datos.", -1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            enviarRespuesta(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    false, "Error interno del servidor.", -1);
        }
    }

    // ─── Método auxiliar ─────────────────────────────────────────────────────

    /**
     * Construye y escribe una respuesta JSON estándar.
     * Incluye opcionalmente el {@code userId} generado para el flujo de onboarding.
     *
     * @param response   Objeto de respuesta HTTP.
     * @param statusCode Código HTTP a establecer.
     * @param success    Resultado booleano de la operación.
     * @param message    Mensaje descriptivo.
     * @param userId     ID del usuario creado, o {@code -1} si no aplica.
     * @throws IOException si ocurre un error de escritura.
     */
    private void enviarRespuesta(HttpServletResponse response,
                                 int statusCode,
                                 boolean success,
                                 String message,
                                 int userId) throws IOException {
        response.setStatus(statusCode);
        JsonObject cuerpo = new JsonObject();
        cuerpo.addProperty("success", success);
        cuerpo.addProperty("message", message);
        if (userId > 0) {
            cuerpo.addProperty("userId", userId); // Incluir solo cuando el registro es exitoso
        }
        response.getWriter().write(gson.toJson(cuerpo));
    }

    // ─── DTO interno ─────────────────────────────────────────────────────────

    /**
     * DTO (Data Transfer Object) para deserializar el payload JSON
     * enviado por {@code Register.jsx}.
     *
     * <p>Campos esperados en el JSON de entrada:
     * <ul>
     *   <li>{@code name}     — Nombre del usuario (opcional, hay valor por defecto).</li>
     *   <li>{@code email}    — Correo electrónico (único en la base de datos).</li>
     *   <li>{@code password} — Contraseña en texto plano.</li>
     * </ul>
     * </p>
     */
    private static class RegisterRequest {
        String name;
        String email;
        String password;
    }
}
