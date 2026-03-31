package com.wisechat.dao;

import com.wisechat.model.ActionsPlan;
import com.wisechat.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del DAO para la entidad ACTIONS_PLAN.
 */
public class PlanAccionDAOImpl implements PlanAccionDAO {

    private final Connection conexion;

    public PlanAccionDAOImpl() {
        this.conexion = ConexionDB.getInstancia().getConexion();
    }

    @Override
    public void crear(ActionsPlan plan) {
        String sql = "INSERT INTO ACTIONS_PLAN (id_business, description, status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, plan.getIdBusiness());
            ps.setString(2, plan.getDescription());
            ps.setString(3, plan.getStatus());
            ps.executeUpdate();
            System.out.println("[PlanAccionDAO] Plan de acción creado.");
        } catch (SQLException e) {
            throw new RuntimeException("[PlanAccionDAO] Error al crear plan: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ActionsPlan> listarTodo() {
        String sql = "SELECT id_action, id_business, description, status, created_at FROM ACTIONS_PLAN";
        List<ActionsPlan> lista = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[PlanAccionDAO] Error al listar planes: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public ActionsPlan buscarPorId(int id) {
        String sql = "SELECT id_action, id_business, description, status, created_at FROM ACTIONS_PLAN WHERE id_action = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[PlanAccionDAO] Error al buscar plan: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void actualizar(ActionsPlan plan) {
        String sql = "UPDATE ACTIONS_PLAN SET description = ?, status = ? WHERE id_action = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, plan.getDescription());
            ps.setString(2, plan.getStatus());
            ps.setInt(3, plan.getIdAction());
            ps.executeUpdate();
            System.out.println("[PlanAccionDAO] Plan actualizado: ID " + plan.getIdAction());
        } catch (SQLException e) {
            throw new RuntimeException("[PlanAccionDAO] Error al actualizar plan: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM ACTIONS_PLAN WHERE id_action = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[PlanAccionDAO] Plan eliminado: ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException("[PlanAccionDAO] Error al eliminar plan: " + e.getMessage(), e);
        }
    }

    private ActionsPlan mapearResultSet(ResultSet rs) throws SQLException {
        return new ActionsPlan(
            rs.getInt("id_action"),
            rs.getInt("id_business"),
            rs.getString("description"),
            rs.getString("status"),
            rs.getTimestamp("created_at")
        );
    }
}
