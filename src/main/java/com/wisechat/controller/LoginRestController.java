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
 * <b>REST Controller — Autenticación de Usuarios (HU-02: Inicio de Sesión)</b>
 *
 * <p>Expone el endpoint {@code POST /api/login} que recibe las credenciales
 * del formulario de Login de React y valida contra la base de datos MySQL
 * mediante JDBC nativo a través de {@link UserDAO}.</p>
 *
 * <p>Flujo de la Historia de Usuario HU-02:</p>
 * <ol>
 *   <li>El frontend envía {@code { "email": "...", "password": "..." }} en JSON.</li>
 *   <li>Este controlador lee el cuerpo de la petición con Gson.</li>
 *   <li>Delega la validación al método {@code UserDAO.autenticar()}.</li>
 *   <li>Si el usuario existe, responde con {@code 200 OK} incluyendo
 *       {@code userId} y {@code nombre} para que React gestione la sesión.</li>
 *   <li>Si las credenciales son inválidas, responde con {@code 401 Unauthorized}.</li>
 * </ol>
 *
 * <p><b>Payload de entrada esperado:</b></p>
 * <pre>
 * {
 *   "email":    "usuario@ejemplo.com",
 *   "password": "contraseña123"
 * }
 * </pre>
 *
 * <p><b>Respuesta exitosa:</b></p>
 * <pre>
 * {
 *   "success": true,
 *   "message": "Inicio de sesión exitoso",
 *   "userId":  1,
 *   "nombre":  "Jhon"
 * }
 * </pre>
 *
 * @author  Wisechat Team
 * @version 2.0 — Evidencia AA3-EV01
 * @see     UserDAO
 * @see     CorsFilter
 */
@WebServlet(name = "LoginRestController", urlPatterns = {"/api/login"})
public class LoginRestController extends HttpServlet {

    /** Interfaz de acceso a datos para la entidad USER. */
    private UserDAO userDAO;

    /** Instancia compartida de Gson para serialización/deserialización JSON. */
    private Gson gson;

    // ─── Inicialización del Servlet ─────────────────────────────────────────

    /**
     * Inicializa las dependencias del controlador:
     * <ul>
     *   <li>{@link UserDAOImpl} para operaciones JDBC sobre la tabla USER.</li>
     *   <li>{@link Gson} para parsear el JSON del cuerpo de la petición.</li>
     * </ul>
     *
     * @throws ServletException si ocurre un error durante la inicialización.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        this.userDAO = new UserDAOImpl();
        this.gson    = new Gson();
    }

    // ─── Handler POST /api/login ─────────────────────────────────────────────

    /**
     * Maneja la petición {@code POST /api/login} enviada por el componente
     * {@code Login.jsx} del frontend React.
     *
     * <p>Valida que los campos {@code email} y {@code password} estén
     * presentes y no vacíos antes de consultar la base de datos.</p>
     *
     * @param request  Petición HTTP con cuerpo JSON {@code { email, password }}.
     * @param response Respuesta HTTP en formato JSON.
     * @throws ServletException si ocurre un error en el procesamiento del Servlet.
     * @throws IOException      si ocurre un error de entrada/salida al leer o escribir.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Establecer tipo de respuesta JSON con codificación UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Leer el cuerpo de la petición y deserializar el JSON
            BufferedReader reader    = request.getReader();
            LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);

            // Validar que el payload no esté vacío ni incompleto
            if (loginRequest == null
                    || loginRequest.email    == null
                    || loginRequest.password == null) {
                enviarRespuesta(response, HttpServletResponse.SC_BAD_REQUEST,
                        false, "Correo y contraseña son requeridos.");
                return;
            }

            // Delegar la autenticación al DAO (consulta JDBC a la tabla USER)
            User usuario = userDAO.autenticar(
                    loginRequest.email.trim(),
                    loginRequest.password.trim()
            );

            if (usuario != null) {
                // Éxito: construir respuesta con datos de sesión (sin contraseña)
                JsonObject respuesta = new JsonObject();
                respuesta.addProperty("success", true);
                respuesta.addProperty("message", "Inicio de sesión exitoso");
                respuesta.addProperty("userId",  usuario.getIdUser());   // Necesario para HU-04 y HU-06
                respuesta.addProperty("nombre",  usuario.getNombre());   // Para mostrar en el Dashboard

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(respuesta));

            } else {
                // Fallo: credenciales inválidas
                enviarRespuesta(response, HttpServletResponse.SC_UNAUTHORIZED,
                        false, "Correo o contraseña incorrectos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            enviarRespuesta(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    false, "Error interno del servidor.");
        }
    }

    // ─── Método auxiliar ─────────────────────────────────────────────────────

    /**
     * Construye y escribe una respuesta JSON estándar de error o estado simple.
     *
     * @param response   Objeto de respuesta HTTP donde se escribe el JSON.
     * @param statusCode Código HTTP que se asignará a la respuesta.
     * @param success    Indica si la operación fue exitosa.
     * @param message    Mensaje descriptivo del resultado de la operación.
     * @throws IOException si ocurre un error al escribir en la respuesta.
     */
    private void enviarRespuesta(HttpServletResponse response,
                                 int statusCode,
                                 boolean success,
                                 String message) throws IOException {
        response.setStatus(statusCode);
        JsonObject cuerpo = new JsonObject();
        cuerpo.addProperty("success", success);
        cuerpo.addProperty("message", message);
        response.getWriter().write(gson.toJson(cuerpo));
    }

    // ─── DTO interno ─────────────────────────────────────────────────────────

    /**
     * DTO (Data Transfer Object) interno para deserializar el cuerpo JSON
     * de la petición de inicio de sesión enviada por {@code Login.jsx}.
     *
     * <p>Campos esperados:
     * <ul>
     *   <li>{@code email}    — Correo electrónico del usuario.</li>
     *   <li>{@code password} — Contraseña en texto plano (el DAO valida contra DB).</li>
     * </ul>
     * </p>
     */
    private static class LoginRequest {
        String email;
        String password;
    }
}
