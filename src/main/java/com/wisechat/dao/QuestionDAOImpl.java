package com.wisechat.dao;

import com.wisechat.model.Question;
import com.wisechat.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del DAO para la entidad QUESTIONS.
 */
public class QuestionDAOImpl implements QuestionDAO {

    public QuestionDAOImpl() {}

    @Override
    public void crear(Question question) {
        String sql = "INSERT INTO QUESTIONS (id_form, question_text, type) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, question.getIdForm());
            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getType());
            ps.executeUpdate();
            System.out.println("[QuestionDAO] Pregunta insertada.");
        } catch (SQLException e) {
            throw new RuntimeException("[QuestionDAO] Error al crear pregunta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Question> listarTodo() {
        String sql = "SELECT id_question, id_form, question_text, type FROM QUESTIONS";
        List<Question> lista = new ArrayList<>();
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[QuestionDAO] Error al listar preguntas: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Question buscarPorId(int id) {
        String sql = "SELECT id_question, id_form, question_text, type FROM QUESTIONS WHERE id_question = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[QuestionDAO] Error al buscar pregunta: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void actualizar(Question question) {
        String sql = "UPDATE QUESTIONS SET question_text = ?, type = ? WHERE id_question = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, question.getQuestionText());
            ps.setString(2, question.getType());
            ps.setInt(3, question.getIdQuestion());
            ps.executeUpdate();
            System.out.println("[QuestionDAO] Pregunta actualizada: ID " + question.getIdQuestion());
        } catch (SQLException e) {
            throw new RuntimeException("[QuestionDAO] Error al actualizar pregunta: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM QUESTIONS WHERE id_question = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[QuestionDAO] Pregunta eliminada: ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException("[QuestionDAO] Error al eliminar pregunta: " + e.getMessage(), e);
        }
    }

    private Question mapearResultSet(ResultSet rs) throws SQLException {
        return new Question(
            rs.getInt("id_question"),
            rs.getInt("id_form"),
            rs.getString("question_text"),
            rs.getString("type")
        );
    }
}
