package com.wisechat.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wisechat.dao.MessageDAO;
import com.wisechat.dao.MessageDAOImpl;
import com.wisechat.model.Message;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Controlador REST para manejar el historial de mensajes de chat (HU-06).
 */
@WebServlet("/api/chat")
public class WiseBotRestController extends HttpServlet {

    private MessageDAO messageDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        // Inicializamos las dependencias
        this.messageDAO = new MessageDAOImpl();
        this.gson = new Gson();
    }

    /**
     * HU-06: Obtener el historial de chat de un usuario.
     * GET /api/chat?userId={id}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String userIdParam = req.getParameter("userId");
        if (userIdParam == null || userIdParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonObject error = new JsonObject();
            error.addProperty("error", "Falta el userId.");
            resp.getWriter().write(gson.toJson(error));
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);
            
            // Obtenemos todos los mensajes (y filtramos en memoria por simplicidad, 
            // aunque lo ideal sería tener un método getByUserId en MessageDAO)
            List<Message> allMessages = messageDAO.listarTodo();
            List<Message> userMessages = allMessages.stream()
                .filter(m -> m.getIdUser() == userId)
                .toList();

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(userMessages));
            
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonObject error = new JsonObject();
            error.addProperty("error", "userId inválido.");
            resp.getWriter().write(gson.toJson(error));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject error = new JsonObject();
            error.addProperty("error", "Error interno al recuperar el chat: " + e.getMessage());
            resp.getWriter().write(gson.toJson(error));
        }
    }

    /**
     * HU-06: Enviar un nuevo mensaje (del usuario o del sistema).
     * POST /api/chat
     * Request Body: { "userId": 1, "messageText": "Hola", "sender": "user" }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        try {
            JsonObject requestBody = gson.fromJson(sb.toString(), JsonObject.class);

            if (!requestBody.has("userId") || !requestBody.has("messageText") || !requestBody.has("sender")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Faltan campos obligatorios: userId, messageText, sender");
                resp.getWriter().write(gson.toJson(error));
                return;
            }

            int userId = requestBody.get("userId").getAsInt();
            String messageText = requestBody.get("messageText").getAsString();
            String sender = requestBody.get("sender").getAsString();

            // Insertar el mensaje
            Message msg = new Message(0, userId, messageText, sender, null);
            messageDAO.crear(msg);

            // Respuesta de éxito
            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonObject res = new JsonObject();
            res.addProperty("status", "success");
            res.addProperty("message", "Mensaje guardado.");
            resp.getWriter().write(gson.toJson(res));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject error = new JsonObject();
            error.addProperty("error", "Error interno al guardar mensaje: " + e.getMessage());
            resp.getWriter().write(gson.toJson(error));
        }
    }
}
