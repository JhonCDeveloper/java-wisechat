package com.wisechat.dao;

import com.wisechat.model.Message;
import com.wisechat.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del DAO para la entidad MESSAGES.
 */
public class MessageDAOImpl implements MessageDAO {

    public MessageDAOImpl() {}

    @Override
    public void crear(Message message) {
        String sql = "INSERT INTO MESSAGES (id_user, message_text, sender) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, message.getIdUser());
            ps.setString(2, message.getMessageText());
            ps.setString(3, message.getSender());
            ps.executeUpdate();
            System.out.println("[MessageDAO] Mensaje creado.");
        } catch (SQLException e) {
            throw new RuntimeException("[MessageDAO] Error al crear mensaje: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Message> listarTodo() {
        String sql = "SELECT id_message, id_user, message_text, sender, created_at FROM MESSAGES";
        List<Message> lista = new ArrayList<>();
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[MessageDAO] Error al listar mensajes: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Message buscarPorId(int id) {
        String sql = "SELECT id_message, id_user, message_text, sender, created_at FROM MESSAGES WHERE id_message = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[MessageDAO] Error al buscar mensaje: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void actualizar(Message message) {
        String sql = "UPDATE MESSAGES SET message_text = ?, sender = ? WHERE id_message = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, message.getMessageText());
            ps.setString(2, message.getSender());
            ps.setInt(3, message.getIdMessage());
            ps.executeUpdate();
            System.out.println("[MessageDAO] Mensaje actualizado: ID " + message.getIdMessage());
        } catch (SQLException e) {
            throw new RuntimeException("[MessageDAO] Error al actualizar mensaje: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM MESSAGES WHERE id_message = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[MessageDAO] Mensaje eliminado: ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException("[MessageDAO] Error al eliminar mensaje: " + e.getMessage(), e);
        }
    }

    private Message mapearResultSet(ResultSet rs) throws SQLException {
        return new Message(
            rs.getInt("id_message"),
            rs.getInt("id_user"),
            rs.getString("message_text"),
            rs.getString("sender"),
            rs.getTimestamp("created_at")
        );
    }
}
