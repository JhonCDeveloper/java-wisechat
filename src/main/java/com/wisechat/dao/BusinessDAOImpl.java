package com.wisechat.dao;

import com.wisechat.model.Business;
import com.wisechat.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de BusinessDAO con JDBC nativo usando el patrón Singleton
 * para la conexión y try-with-resources para garantizar el control de recursos.
 */
public class BusinessDAOImpl implements BusinessDAO {

    @Override
    public void insertarNegocio(Business negocio) {
        String sql = "INSERT INTO BUSINESS (id_user, name, industry, description) VALUES (?, ?, ?, ?)";

        // Se usa try-with-resources para Connection y PreparedStatement
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, negocio.getIdUser());
            ps.setString(2, negocio.getNombre());
            ps.setString(3, negocio.getIndustria());
            ps.setString(4, negocio.getDescripcion());

            ps.executeUpdate();
            System.out.println("[BusinessDAOImpl] Negocio insertado correctamente.");

        } catch (SQLException e) {
            System.err.println("[BusinessDAOImpl] Error insertarNegocio: " + e.getMessage());
        }
    }

    @Override
    public List<Business> listarNegocios() {
        String sql = "SELECT id_business, id_user, name, industry, description FROM BUSINESS";
        List<Business> list = new ArrayList<>();

        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapearNegocio(rs));
            }

        } catch (SQLException e) {
            System.err.println("[BusinessDAOImpl] Error listarNegocios: " + e.getMessage());
        }

        return list;
    }

    @Override
    public Business consultarNegocio(int id) {
        String sql = "SELECT id_business, id_user, name, industry, description FROM BUSINESS WHERE id_business = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearNegocio(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[BusinessDAOImpl] Error consultarNegocio: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizarNegocio(Business negocio) {
        String sql = "UPDATE BUSINESS SET name = ?, industry = ?, description = ? WHERE id_business = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, negocio.getNombre());
            ps.setString(2, negocio.getIndustria());
            ps.setString(3, negocio.getDescripcion());
            ps.setInt(4, negocio.getIdBusiness());

            ps.executeUpdate();
            System.out.println("[BusinessDAOImpl] Negocio actualizado correctamente.");

        } catch (SQLException e) {
            System.err.println("[BusinessDAOImpl] Error actualizarNegocio: " + e.getMessage());
        }
    }

    @Override
    public void eliminarNegocio(int id) {
        String sql = "DELETE FROM BUSINESS WHERE id_business = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("[BusinessDAOImpl] Negocio eliminado correctamente.");

        } catch (SQLException e) {
            System.err.println("[BusinessDAOImpl] Error eliminarNegocio: " + e.getMessage());
        }
    }

    // Método auxiliar unicamente para el mapeo del ResultSet
    private Business mapearNegocio(ResultSet rs) throws SQLException {
        return new Business(
            rs.getInt("id_business"),
            rs.getInt("id_user"),
            rs.getString("name"),
            rs.getString("industry"),
            rs.getString("description")
        );
    }
}
