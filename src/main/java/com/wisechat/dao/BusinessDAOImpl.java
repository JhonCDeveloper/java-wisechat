package com.wisechat.dao;

import com.wisechat.model.Business;
import com.wisechat.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del DAO para la entidad BUSINESS.
 */
public class BusinessDAOImpl implements BusinessDAO {

    private final Connection conexion;

    public BusinessDAOImpl() {
        this.conexion = ConexionDB.getInstancia().getConexion();
    }

    @Override
    public void crear(Business business) {
        String sql = "INSERT INTO BUSINESS (id_user, name, industry, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, business.getIdUser());
            ps.setString(2, business.getName());
            ps.setString(3, business.getIndustry());
            ps.setString(4, business.getDescription());
            ps.executeUpdate();
            System.out.println("[BusinessDAO] Empresa creada: " + business.getName());
        } catch (SQLException e) {
            throw new RuntimeException("[BusinessDAO] Error al crear empresa: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Business> listarTodo() {
        String sql = "SELECT id_business, id_user, name, industry, description FROM BUSINESS";
        List<Business> lista = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[BusinessDAO] Error al listar empresas: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public Business buscarPorId(int id) {
        String sql = "SELECT id_business, id_user, name, industry, description FROM BUSINESS WHERE id_business = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[BusinessDAO] Error al buscar empresa: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void actualizar(Business business) {
        String sql = "UPDATE BUSINESS SET name = ?, industry = ?, description = ? WHERE id_business = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, business.getName());
            ps.setString(2, business.getIndustry());
            ps.setString(3, business.getDescription());
            ps.setInt(4, business.getIdBusiness());
            ps.executeUpdate();
            System.out.println("[BusinessDAO] Empresa actualizada: ID " + business.getIdBusiness());
        } catch (SQLException e) {
            throw new RuntimeException("[BusinessDAO] Error al actualizar empresa: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM BUSINESS WHERE id_business = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[BusinessDAO] Empresa eliminada: ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException("[BusinessDAO] Error al eliminar empresa: " + e.getMessage(), e);
        }
    }

    private Business mapearResultSet(ResultSet rs) throws SQLException {
        return new Business(
            rs.getInt("id_business"),
            rs.getInt("id_user"),
            rs.getString("name"),
            rs.getString("industry"),
            rs.getString("description")
        );
    }
}
