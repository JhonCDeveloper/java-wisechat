package com.wisechat.dao;

import com.wisechat.model.Response;
import com.wisechat.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del DAO para la entidad RESPONSES.
 */
public class ResponseDAOImpl implements ResponseDAO {

    public ResponseDAOImpl() {}

    @Override
    public void crear(Response response) {
        // En wisechat_db el esquema establece id_question si existe una tabla QUESTIONS con Constraint
        // Para compatibilidad y evitar error Constraint FK "id_question", usamos inserción flexible.
        // Si no usamos foreign keys estrictas, el insert normal funciona.
        String sql = "INSERT INTO RESPONSES (id_form, id_question, id_user, answer) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, response.getIdForm());
            
            // Ajuste crucial: si idQuestion es 0, enviamos NULL a la BDD para evitar constraint FK error
            if (response.getIdQuestion() == 0) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, response.getIdQuestion());
            }

            ps.setInt(3, response.getIdUser());
            ps.setString(4, response.getAnswer());
            ps.executeUpdate();
            System.out.println("[ResponseDAO] Respuesta registrada.");
        } catch (SQLException e) {
            throw new RuntimeException("[ResponseDAO] Error al crear respuesta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Response> listarTodo() {
        String sql = "SELECT id_response, id_form, id_question, id_user, answer, created_at FROM RESPONSES";
        List<Response> lista = new ArrayList<>();
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ResponseDAO] Error al listar respuestas: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Response buscarPorId(int id) {
        String sql = "SELECT id_response, id_form, id_question, id_user, answer, created_at FROM RESPONSES WHERE id_response = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ResponseDAO] Error al buscar respuesta: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void actualizar(Response response) {
        String sql = "UPDATE RESPONSES SET answer = ? WHERE id_response = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, response.getAnswer());
            ps.setInt(2, response.getIdResponse());
            ps.executeUpdate();
            System.out.println("[ResponseDAO] Respuesta actualizada: ID " + response.getIdResponse());
        } catch (SQLException e) {
            throw new RuntimeException("[ResponseDAO] Error al actualizar respuesta: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM RESPONSES WHERE id_response = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[ResponseDAO] Respuesta eliminada: ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException("[ResponseDAO] Error al eliminar respuesta: " + e.getMessage(), e);
        }
    }

    private Response mapearResultSet(ResultSet rs) throws SQLException {
        return new Response(
            rs.getInt("id_response"),
            rs.getInt("id_form"),
            rs.getInt("id_question"),
            rs.getInt("id_user"),
            rs.getString("answer"),
            rs.getTimestamp("created_at")
        );
    }
}
