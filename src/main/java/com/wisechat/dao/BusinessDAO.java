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
 * Clase DAO para la entidad BUSINESS.
 *
 * Gestiona todas las operaciones CRUD sobre la tabla BUSINESS
 * usando JDBC nativo con PreparedStatement.
 *
 * Métodos disponibles:
 *   - insertarEmpresa(Business)
 *   - consultarEmpresaPorId(int)
 *   - listarTodasLasEmpresas()
 *   - actualizarEmpresa(Business)
 *   - eliminarEmpresa(int)
 */
public class BusinessDAO {

    // ─── Conexión compartida desde el Singleton ───────────────────────────────
    private final Connection conexion;

    /**
     * Constructor: obtiene la conexión del Singleton ConexionDB.
     */
    public BusinessDAO() {
        this.conexion = ConexionDB.getInstancia().getConexion();
    }

    // =========================================================================
    // INSERTAR
    // =========================================================================

    /**
     * Inserta una nueva empresa en la tabla BUSINESS.
     *
     * @param empresa Objeto Business con idUser, nombre, industria y descripcion.
     *                El idBusiness es ignorado (AUTO_INCREMENT en BD).
     */
    public void insertarEmpresa(Business empresa) {
        String sql = "INSERT INTO BUSINESS (id_user, name, industry, description) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1,    empresa.getIdUser());
            ps.setString(2, empresa.getNombre());
            ps.setString(3, empresa.getIndustria());
            ps.setString(4, empresa.getDescripcion());

            int filasAfectadas = ps.executeUpdate();
            System.out.println("[BusinessDAO] Empresa insertada correctamente. "
                    + "Filas afectadas: " + filasAfectadas);

        } catch (SQLException e) {
            System.err.println("[BusinessDAO] Error al insertar empresa: " + e.getMessage());
        }
    }

    // =========================================================================
    // CONSULTAR POR ID
    // =========================================================================

    /**
     * Consulta una empresa por su ID primario.
     *
     * @param id Identificador de la empresa (id_business).
     * @return   Objeto Business si existe, null si no se encontró.
     */
    public Business consultarEmpresaPorId(int id) {
        String sql = "SELECT id_business, id_user, name, industry, description "
                   + "FROM BUSINESS WHERE id_business = ?";
        Business empresa = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    empresa = mapearResultSet(rs);
                    System.out.println("[BusinessDAO] Empresa encontrada: " + empresa);
                } else {
                    System.out.println("[BusinessDAO] No se encontró empresa con ID: " + id);
                }
            }

        } catch (SQLException e) {
            System.err.println("[BusinessDAO] Error al consultar empresa por ID: " + e.getMessage());
        }

        return empresa;
    }

    // =========================================================================
    // LISTAR TODAS
    // =========================================================================

    /**
     * Recupera todas las empresas registradas en la tabla BUSINESS.
     *
     * @return Lista de objetos Business (vacía si no hay registros).
     */
    public List<Business> listarTodasLasEmpresas() {
        String sql = "SELECT id_business, id_user, name, industry, description "
                   + "FROM BUSINESS";
        List<Business> lista = new ArrayList<>();

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
            System.out.println("[BusinessDAO] Total de empresas recuperadas: " + lista.size());

        } catch (SQLException e) {
            System.err.println("[BusinessDAO] Error al listar empresas: " + e.getMessage());
        }

        return lista;
    }

    // =========================================================================
    // ACTUALIZAR
    // =========================================================================

    /**
     * Actualiza los datos de una empresa existente en la tabla BUSINESS.
     * Se identifica por id_business.
     *
     * @param empresa Objeto Business con los datos actualizados (incluido idBusiness).
     */
    public void actualizarEmpresa(Business empresa) {
        String sql = "UPDATE BUSINESS SET name = ?, industry = ?, description = ? "
                   + "WHERE id_business = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, empresa.getNombre());
            ps.setString(2, empresa.getIndustria());
            ps.setString(3, empresa.getDescripcion());
            ps.setInt(4,    empresa.getIdBusiness());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[BusinessDAO] Empresa actualizada correctamente. ID: "
                        + empresa.getIdBusiness());
            } else {
                System.out.println("[BusinessDAO] No se encontró empresa con ID: "
                        + empresa.getIdBusiness() + ". No se realizó ninguna actualización.");
            }

        } catch (SQLException e) {
            System.err.println("[BusinessDAO] Error al actualizar empresa: " + e.getMessage());
        }
    }

    // =========================================================================
    // ELIMINAR
    // =========================================================================

    /**
     * Elimina una empresa de la tabla BUSINESS según su ID.
     * Por la restricción ON DELETE CASCADE, también se eliminarán los
     * formularios, preguntas, respuestas y planes de acción asociados.
     *
     * @param id Identificador de la empresa a eliminar (id_business).
     */
    public void eliminarEmpresa(int id) {
        String sql = "DELETE FROM BUSINESS WHERE id_business = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[BusinessDAO] Empresa eliminada correctamente. ID: " + id);
            } else {
                System.out.println("[BusinessDAO] No se encontró empresa con ID: " + id
                        + ". No se eliminó ningún registro.");
            }

        } catch (SQLException e) {
            System.err.println("[BusinessDAO] Error al eliminar empresa: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODO AUXILIAR — Mapeo de ResultSet a objeto Business
    // =========================================================================

    /**
     * Convierte una fila del ResultSet en un objeto Business.
     *
     * @param rs ResultSet posicionado en una fila válida.
     * @return   Objeto Business con los datos de la fila.
     * @throws SQLException Si ocurre un error al leer las columnas.
     */
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
