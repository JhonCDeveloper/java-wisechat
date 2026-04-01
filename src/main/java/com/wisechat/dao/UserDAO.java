package com.wisechat.dao;

import com.wisechat.model.User;
import java.util.List;

/**
 * Interfaz UserDAO que define las operaciones CRUD
 * requeridas para la tabla USER por el SENA.
 */
public interface UserDAO {
    int insertarUsuario(User usuario);
    List<User> listarUsuarios();
    User consultarUsuario(int id);
    void actualizarUsuario(User usuario);
    void eliminarUsuario(int id);
}
