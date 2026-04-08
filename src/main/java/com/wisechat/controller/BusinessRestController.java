package com.wisechat.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wisechat.dao.BusinessDAO;
import com.wisechat.dao.BusinessDAOImpl;
import com.wisechat.dao.FormDAO;
import com.wisechat.dao.FormDAOImpl;
import com.wisechat.dao.ResponseDAO;
import com.wisechat.dao.ResponseDAOImpl;
import com.wisechat.model.Business;
import com.wisechat.model.Form;
import com.wisechat.model.Response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * <b>REST Controller — Onboarding del Negocio (HU-04: Formulario de Configuración)</b>
 *
 * <p>Expone dos endpoints que juntos procesan el formulario wizard de 11 preguntas
 * del componente {@code Form.jsx} del frontend React:</p>
 *
 * <ul>
 *   <li>{@code POST /api/business} — Crea el registro principal del negocio en la
 *       tabla {@code BUSINESS} usando los campos estructurales del formulario
 *       ({@code negocio}, {@code sector}, {@code productos}).</li>
 *   <li>{@code GET /api/business?userId={id}} — Consulta el negocio registrado
 *       por un usuario específico, útil para mostrar datos en el Dashboard.</li>
 * </ul>
 *
 * <p>Flujo completo de la Historia de Usuario HU-04:</p>
 * <ol>
 *   <li>El usuario completa el wizard de 11 preguntas en {@code Form.jsx}.</li>
 *   <li>Al hacer clic en "Finalizar", React envía un JSON con todos los campos.</li>
 *   <li>Este controller crea un registro en {@code BUSINESS} con los datos del negocio.</li>
 *   <li>Adicionalmente, crea un {@code FORM} enlazado al Business, y persiste
 *       cada respuesta como un registro en la tabla {@code RESPONSES}.</li>
 *   <li>La respuesta incluye {@code idBusiness} para que React lo almacene en sesión.</li>
 * </ol>
 *
 * <p><b>Payload de entrada esperado en POST /api/business:</b></p>
 * <pre>
 * {
 *   "idUser":       1,
 *   "negocio":      "Mi Tienda",
 *   "rol":          "Jhon, CEO",
 *   "sector":       "Tecnología",
 *   "productos":    "Ropa deportiva y accesorios",
 *   "dondeVendes":  "Solo en redes sociales",
 *   "cliente":      "Jóvenes 18-30, deportistas",
 *   "mensajes":     "Entre 10 y 30",
 *   "herramientas": "WhatsApp Business",
 *   "dificultad":   "Cerrar la venta",
 *   "equipo":       "Sí (¿cuántas personas?)",
 *   "equipoDetalle":"3 personas",
 *   "sugerencias":  "Sí"
 * }
 * </pre>
 *
 * <p><b>Respuesta exitosa POST:</b></p>
 * <pre>
 * {
 *   "success":     true,
 *   "message":     "Negocio y respuestas registrados correctamente.",
 *   "idBusiness":  7
 * }
 * </pre>
 *
 * @author  Wisechat Team
 * @version 1.0 — Evidencia AA3-EV01
 * @see     BusinessDAO
 * @see     FormDAO
 * @see     ResponseDAO
 */
@WebServlet(name = "BusinessRestController", urlPatterns = {"/api/business"})
public class BusinessRestController extends HttpServlet {

    /** DAO para operaciones CRUD sobre la tabla BUSINESS. */
    private BusinessDAO businessDAO;

    /** DAO para operaciones CRUD sobre la tabla FORM. */
    private FormDAO formDAO;

    /** DAO para operaciones CRUD sobre la tabla RESPONSES. */
    private ResponseDAO responseDAO;

    /** Instancia de Gson para serialización y deserialización JSON. */
    private Gson gson;

    // ─── Inicialización ──────────────────────────────────────────────────────

    /**
     * Inicializa los DAOs necesarios para procesar el onboarding completo:
     * Business, Form y Response.
     *
     * @throws ServletException si falla la inicialización del Servlet.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        this.businessDAO = new BusinessDAOImpl();
        this.formDAO     = new FormDAOImpl();
        this.responseDAO = new ResponseDAOImpl();
        this.gson        = new Gson();
    }

    // ─── Handler POST /api/business ──────────────────────────────────────────

    /**
     * Procesa el envío completo del formulario wizard de onboarding (HU-04).
     *
     * <p>Pasos internos:
     * <ol>
     *   <li>Deserializa el JSON recibido de {@code Form.jsx}.</li>
     *   <li>Crea el registro {@code BUSINESS} con nombre, industria y descripción.</li>
     *   <li>Crea un registro {@code FORM} asociado al Business.</li>
     *   <li>Persiste cada pregunta/respuesta del wizard como un {@code RESPONSE}.</li>
     * </ol>
     * </p>
     *
     * @param request  Petición HTTP con el JSON completo del wizard de onboarding.
     * @param response Respuesta HTTP en formato JSON con el {@code idBusiness} generado.
     * @throws ServletException si ocurre un error de Servlet.
     * @throws IOException      si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Deserializar el payload completo del formulario de onboarding
            BufferedReader reader         = request.getReader();
            OnboardingRequest onboarding  = gson.fromJson(reader, OnboardingRequest.class);

            // Validar campos mínimos obligatorios
            if (onboarding == null || onboarding.idUser <= 0 || onboarding.negocio == null) {
                enviarRespuesta(response, HttpServletResponse.SC_BAD_REQUEST,
                        false, "Datos de negocio incompletos. Se requiere idUser y nombre del negocio.", -1);
                return;
            }

            // ── Paso 1: Crear el registro BUSINESS ──────────────────────────────
            // Mapeamos los campos del wizard a la entidad Business:
            //   negocio   → nombre
            //   sector    → industria
            //   productos → descripcion
            Business nuevoNegocio = new Business(
                    onboarding.idUser,
                    onboarding.negocio.trim(),
                    onboarding.sector  != null ? onboarding.sector.trim()   : "Sin especificar",
                    onboarding.productos != null ? onboarding.productos.trim() : "Sin especificar"
            );
            businessDAO.insertarNegocio(nuevoNegocio);

            // Obtener el negocio recién creado para saber el idBusiness generado
            List<Business> negocios   = businessDAO.listarNegocios();
            Business negocioCreado    = negocios.isEmpty() ? null : negocios.get(negocios.size() - 1);
            int idBusiness            = (negocioCreado != null) ? negocioCreado.getIdBusiness() : -1;

            // ── Paso 2: Crear el FORM asociado a este Business ──────────────────
            if (idBusiness > 0) {
                Form formulario = new Form(idBusiness, "Onboarding Inicial");
                formDAO.crear(formulario);

                // Obtener el Form recién creado para saber el idForm generado
                List<Form> formularios = formDAO.listarTodo();
                Form formularioCreado  = formularios.isEmpty() ? null : formularios.get(formularios.size() - 1);
                int  idForm            = (formularioCreado != null) ? formularioCreado.getIdForm() : -1;

                // ── Paso 3: Persistir cada respuesta del wizard como RESPONSE ────
                if (idForm > 0) {
                    guardarRespuestas(onboarding, idForm, onboarding.idUser);
                }
            }

            // Respuesta exitosa con el ID del negocio creado
            enviarRespuesta(response, HttpServletResponse.SC_CREATED,
                    true, "Negocio y respuestas registrados correctamente.", idBusiness);

        } catch (Exception e) {
            e.printStackTrace();
            enviarRespuesta(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    false, "Error interno al procesar el formulario.", -1);
        }
    }

    // ─── Handler GET /api/business?userId={id} ───────────────────────────────

    /**
     * Consulta los negocios registrados por un usuario.
     * Útil para que el Dashboard muestre los datos del negocio activo.
     *
     * <p>Parámetro de query: {@code userId} — ID del usuario propietario.</p>
     *
     * <p><b>Respuesta:</b></p>
     * <pre>
     * {
     *   "success": true,
     *   "data": [{ "idBusiness": 1, "nombre": "Mi Tienda", ... }]
     * }
     * </pre>
     *
     * @param request  Petición HTTP con parámetro {@code userId} en la query string.
     * @param response Respuesta HTTP en formato JSON con la lista de negocios.
     * @throws ServletException si ocurre un error de Servlet.
     * @throws IOException      si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String userIdParam = request.getParameter("userId");
            if (userIdParam == null || userIdParam.isBlank()) {
                enviarRespuesta(response, HttpServletResponse.SC_BAD_REQUEST,
                        false, "Parámetro userId es requerido.", -1);
                return;
            }

            int userId = Integer.parseInt(userIdParam);

            // Obtener todos los negocios y filtrar por usuario
            List<Business> todosNegocios = businessDAO.listarNegocios();
            List<Business> misNegocios   = todosNegocios.stream()
                    .filter(b -> b.getIdUser() == userId)
                    .toList();

            // Construir respuesta JSON con la lista de negocios
            JsonObject respuesta = new JsonObject();
            respuesta.addProperty("success", true);
            respuesta.add("data", gson.toJsonTree(misNegocios));

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(respuesta));

        } catch (NumberFormatException e) {
            enviarRespuesta(response, HttpServletResponse.SC_BAD_REQUEST,
                    false, "El parámetro userId debe ser un número entero.", -1);
        } catch (Exception e) {
            e.printStackTrace();
            enviarRespuesta(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    false, "Error interno al consultar negocios.", -1);
        }
    }

    // ─── Métodos auxiliares ──────────────────────────────────────────────────

    /**
     * Persiste cada respuesta del wizard de onboarding como un registro
     * individual en la tabla {@code RESPONSES}.
     * Esto permite analizar las respuestas por pregunta en el futuro.
     *
     * @param onboarding Payload completo del formulario de React.
     * @param idForm     ID del formulario creado en la tabla FORM.
     * @param idUser     ID del usuario que completó el formulario.
     */
    private void guardarRespuestas(OnboardingRequest onboarding, int idForm, int idUser) {
        // Mapa de preguntas del wizard con sus respuestas del formulario
        String[][] preguntasRespuestas = {
            { "¿Cuál es tu nombre y tu rol?",         onboarding.rol           },
            { "¿Dónde vendes tus productos?",         onboarding.dondeVendes   },
            { "¿Cómo es tu cliente ideal?",            onboarding.cliente       },
            { "¿Cuántos mensajes recibes al día?",     onboarding.mensajes      },
            { "¿Qué herramientas usas?",              onboarding.herramientas  },
            { "¿Cuál es tu mayor dificultad?",         onboarding.dificultad   },
            { "¿Tienes equipo de ventas?",             onboarding.equipo        },
            { "¿Cuántas personas en tu equipo?",       onboarding.equipoDetalle },
            { "¿Quieres sugerencias automáticas?",     onboarding.sugerencias   }
        };

        for (String[] par : preguntasRespuestas) {
            String pregunta  = par[0];
            String respuesta = (par[1] != null) ? par[1].trim() : "Sin respuesta";

            // Crear y persistir cada respuesta en la tabla RESPONSES
            Response resp = new Response(idForm, 0, idUser, respuesta);
            responseDAO.crear(resp);
            System.out.println("[BusinessRestController] Respuesta guardada — "
                    + pregunta + ": " + respuesta);
        }
    }

    /**
     * Escribe una respuesta JSON estándar con código HTTP, resultado e ID opcional.
     *
     * @param response   Respuesta HTTP donde escribir el JSON.
     * @param statusCode Código de estado HTTP.
     * @param success    Resultado booleano de la operación.
     * @param message    Mensaje descriptivo de la operación.
     * @param id         ID del recurso creado, o {@code -1} si no corresponde.
     * @throws IOException si ocurre un error de escritura.
     */
    private void enviarRespuesta(HttpServletResponse response,
                                 int statusCode,
                                 boolean success,
                                 String message,
                                 int id) throws IOException {
        response.setStatus(statusCode);
        JsonObject cuerpo = new JsonObject();
        cuerpo.addProperty("success", success);
        cuerpo.addProperty("message", message);
        if (id > 0) {
            cuerpo.addProperty("idBusiness", id);
        }
        response.getWriter().write(gson.toJson(cuerpo));
    }

    // ─── DTO interno ─────────────────────────────────────────────────────────

    /**
     * DTO (Data Transfer Object) que mapea los 11 campos del wizard de onboarding
     * enviados por {@code Form.jsx}.
     *
     * <p>Corresponde completamente al objeto {@code formData} del estado React:
     * <ul>
     *   <li>{@code idUser}       — ID del usuario autenticado (de la sesión React).</li>
     *   <li>{@code negocio}      — Nombre del negocio → columna {@code BUSINESS.name}.</li>
     *   <li>{@code rol}          — Nombre y cargo del propietario.</li>
     *   <li>{@code sector}       — Industria → columna {@code BUSINESS.industry}.</li>
     *   <li>{@code productos}    — Descripción del negocio → {@code BUSINESS.description}.</li>
     *   <li>{@code dondeVendes}  — Canal de ventas → {@code RESPONSES}.</li>
     *   <li>{@code cliente}      — Perfil del cliente ideal → {@code RESPONSES}.</li>
     *   <li>{@code mensajes}     — Volumen de mensajes → {@code RESPONSES}.</li>
     *   <li>{@code herramientas} — Herramientas de comunicación → {@code RESPONSES}.</li>
     *   <li>{@code dificultad}   — Principal dificultad de venta → {@code RESPONSES}.</li>
     *   <li>{@code equipo}       — Si tiene equipo → {@code RESPONSES}.</li>
     *   <li>{@code equipoDetalle}— Detalle del equipo → {@code RESPONSES}.</li>
     *   <li>{@code sugerencias}  — Acepta sugerencias automáticas → {@code RESPONSES}.</li>
     * </ul>
     * </p>
     */
    private static class OnboardingRequest {
        int    idUser;
        String negocio;
        String rol;
        String sector;
        String productos;
        String dondeVendes;
        String cliente;
        String mensajes;
        String herramientas;
        String dificultad;
        String equipo;
        String equipoDetalle;
        String sugerencias;
    }
}
