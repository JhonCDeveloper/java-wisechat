package com.wisechat.dao;

import com.wisechat.model.Form;
import com.wisechat.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del DAO para la entidad FORM.
 */
public class FormDAOImpl implements FormDAO {

    public FormDAOImpl() {}

    @Override
    public void crear(Form form) {
        String sql = "INSERT INTO FORM (id_business, title) VALUES (?, ?)";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, form.getIdBusiness());
            ps.setString(2, form.getTitle());
            ps.executeUpdate();
            System.out.println("[FormDAO] Formulario creado: " + form.getTitle());
        } catch (SQLException e) {
            throw new RuntimeException("[FormDAO] Error al crear formulario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Form> listarTodo() {
        String sql = "SELECT id_form, id_business, title, created_at FROM FORM";
        List<Form> lista = new ArrayList<>();
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[FormDAO] Error al listar formularios: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Form buscarPorId(int id) {
        String sql = "SELECT id_form, id_business, title, created_at FROM FORM WHERE id_form = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[FormDAO] Error al buscar formulario: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void actualizar(Form form) {
        String sql = "UPDATE FORM SET title = ? WHERE id_form = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, form.getTitle());
            ps.setInt(2, form.getIdForm());
            ps.executeUpdate();
            System.out.println("[FormDAO] Formulario actualizado: ID " + form.getIdForm());
        } catch (SQLException e) {
            throw new RuntimeException("[FormDAO] Error al actualizar formulario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM FORM WHERE id_form = ?";
        try (Connection conexion = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[FormDAO] Formulario eliminado: ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException("[FormDAO] Error al eliminar formulario: " + e.getMessage(), e);
        }
    }

    private Form mapearResultSet(ResultSet rs) throws SQLException {
        return new Form(
            rs.getInt("id_form"),
            rs.getInt("id_business"),
            rs.getString("title"),
            rs.getTimestamp("created_at")
        );
    }
}
