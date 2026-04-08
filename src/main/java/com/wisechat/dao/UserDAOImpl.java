package com.wisechat.dao;

import com.wisechat.model.User;
import com.wisechat.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de UserDAO con JDBC nativo usando el patrón Singleton
 * para la conexión y try-with-resources para el control de recursos.
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public int insertarUsuario(User usuario) {
        String sql = "INSERT INTO USER (name, email, password) VALUES (?, ?, ?)";
        
        // try-with-resources asegura que la conexión y el statement se cierren siempre automáticamente
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());

            ps.executeUpdate();
            
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserDAOImpl] Error insertarUsuario: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public List<User> listarUsuarios() {
        String sql = "SELECT id_user, name, email, password, created_at FROM USER";
        List<User> list = new ArrayList<>();

        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapearUsuario(rs));
            }

        } catch (SQLException e) {
            System.err.println("[UserDAOImpl] Error listarUsuarios: " + e.getMessage());
        }

        return list;
    }

    @Override
    public User consultarUsuario(int id) {
        String sql = "SELECT id_user, name, email, password, created_at FROM USER WHERE id_user = ?";
        
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserDAOImpl] Error consultarUsuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizarUsuario(User usuario) {
        String sql = "UPDATE USER SET name = ?, email = ?, password = ? WHERE id_user = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setInt(4, usuario.getIdUser());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("[UserDAOImpl] Error actualizarUsuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM USER WHERE id_user = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("[UserDAOImpl] Error eliminarUsuario: " + e.getMessage());
        }
    }

    @Override
    public User buscarPorEmail(String email) {
        String sql = "SELECT id_user, name, email, password, created_at FROM USER WHERE email = ?";
        
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserDAOImpl] Error buscarPorEmail: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User autenticar(String email, String password) {
        String sql = "SELECT id_user, name, email, password, created_at FROM USER WHERE email = ? AND password = ?";
        
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserDAOImpl] Error autenticar: " + e.getMessage());
        }
        return null;
    }

    // Método auxiliar unicamente para estructurar el ResultSet
    private User mapearUsuario(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id_user"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getTimestamp("created_at")
        );
    }
}
