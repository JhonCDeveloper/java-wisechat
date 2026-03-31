package com.wisechat.dao;

import com.wisechat.model.User;
import com.wisechat.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para la entidad USER.
 *
 * Gestiona todas las operaciones CRUD sobre la tabla USER
 * usando JDBC nativo con PreparedStatement.
 *
 * Métodos disponibles:
 *   - insertarUsuario(User)
 *   - consultarUsuarioPorId(int)
 *   - listarTodosLosUsuarios()
 *   - actualizarUsuario(User)
 *   - eliminarUsuario(int)
 */
public class UserDAO {

    // ─── Conexión compartida desde el Singleton ───────────────────────────────
    private final Connection conexion;

    /**
     * Constructor: obtiene la conexión del Singleton ConexionDB.
     */
    public UserDAO() {
        this.conexion = ConexionDB.getInstancia().getConexion();
    }

    // =========================================================================
    // INSERTAR
    // =========================================================================

    /**
     * Inserta un nuevo usuario en la tabla USER.
     *
     * @param usuario Objeto User con nombre, email y password (id ignorado).
     */
    public void insertarUsuario(User usuario) {
        String sql = "INSERT INTO USER (name, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());

            int filasAfectadas = ps.executeUpdate();
            System.out.println("[UserDAO] Usuario insertado correctamente. "
                    + "Filas afectadas: " + filasAfectadas);

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error al insertar usuario: " + e.getMessage());
        }
    }

    // =========================================================================
    // CONSULTAR POR ID
    // =========================================================================

    /**
     * Consulta un usuario por su ID primario.
     *
     * @param id Identificador del usuario (id_user).
     * @return   Objeto User si existe, null si no se encontró.
     */
    public User consultarUsuarioPorId(int id) {
        String sql = "SELECT id_user, name, email, password, created_at "
                   + "FROM USER WHERE id_user = ?";
        User usuario = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearResultSet(rs);
                    System.out.println("[UserDAO] Usuario encontrado: " + usuario);
                } else {
                    System.out.println("[UserDAO] No se encontró usuario con ID: " + id);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error al consultar usuario por ID: " + e.getMessage());
        }

        return usuario;
    }

    // =========================================================================
    // LISTAR TODOS
    // =========================================================================

    /**
     * Recupera todos los usuarios registrados en la tabla USER.
     *
     * @return Lista de objetos User (vacía si no hay registros).
     */
    public List<User> listarTodosLosUsuarios() {
        String sql   = "SELECT id_user, name, email, password, created_at FROM USER";
        List<User> lista = new ArrayList<>();

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
            System.out.println("[UserDAO] Total de usuarios recuperados: " + lista.size());

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    // =========================================================================
    // ACTUALIZAR
    // =========================================================================

    /**
     * Actualiza los datos de un usuario existente en la tabla USER.
     * Se identifican por id_user.
     *
     * @param usuario Objeto User con los datos actualizados (incluido idUser).
     */
    public void actualizarUsuario(User usuario) {
        String sql = "UPDATE USER SET name = ?, email = ?, password = ? "
                   + "WHERE id_user = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setInt(4,    usuario.getIdUser());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[UserDAO] Usuario actualizado correctamente. ID: "
                        + usuario.getIdUser());
            } else {
                System.out.println("[UserDAO] No se encontró usuario con ID: "
                        + usuario.getIdUser() + ". No se realizó ninguna actualización.");
            }

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error al actualizar usuario: " + e.getMessage());
        }
    }

    // =========================================================================
    // ELIMINAR
    // =========================================================================

    /**
     * Elimina un usuario de la tabla USER según su ID.
     *
     * @param id Identificador del usuario a eliminar (id_user).
     */
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM USER WHERE id_user = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[UserDAO] Usuario eliminado correctamente. ID: " + id);
            } else {
                System.out.println("[UserDAO] No se encontró usuario con ID: " + id
                        + ". No se eliminó ningún registro.");
            }

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error al eliminar usuario: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODO AUXILIAR — Mapeo de ResultSet a objeto User
    // =========================================================================

    /**
     * Convierte una fila del ResultSet en un objeto User.
     *
     * @param rs ResultSet posicionado en una fila válida.
     * @return   Objeto User con los datos de la fila.
     * @throws SQLException Si ocurre un error al leer las columnas.
     */
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
