package com.wisechat.dao;

import com.wisechat.model.User;
import com.wisechat.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del DAO para la entidad USER.
 * Usa PreparedStatement para todas las operaciones.
 */
public class UserDAOImpl implements UserDAO {

    private final Connection conexion;

    public UserDAOImpl() {
        this.conexion = ConexionDB.getInstancia().getConexion();
    }

    @Override
    public void crear(User user) {
        String sql = "INSERT INTO USER (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
            System.out.println("[UserDAO] Usuario creado: " + user.getName());
        } catch (SQLException e) {
            throw new RuntimeException("[UserDAO] Error al crear usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> listarTodo() {
        String sql = "SELECT id_user, name, email, password, created_at FROM USER";
        List<User> lista = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[UserDAO] Error al listar usuarios: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public User buscarPorId(int id) {
        String sql = "SELECT id_user, name, email, password, created_at FROM USER WHERE id_user = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[UserDAO] Error al buscar usuario por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void actualizar(User user) {
        String sql = "UPDATE USER SET name = ?, email = ?, password = ? WHERE id_user = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getIdUser());
            ps.executeUpdate();
            System.out.println("[UserDAO] Usuario actualizado: ID " + user.getIdUser());
        } catch (SQLException e) {
            throw new RuntimeException("[UserDAO] Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM USER WHERE id_user = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[UserDAO] Usuario eliminado: ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException("[UserDAO] Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    private User mapearResultSet(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id_user"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getTimestamp("created_at")
        );
    }
}
